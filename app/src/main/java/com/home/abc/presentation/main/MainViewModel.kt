package com.home.abc.presentation.main

import androidx.lifecycle.ViewModel
import com.home.abc.domain.model.ListItem
import com.home.abc.domain.model.Page
import com.home.abc.domain.repository.PagesRepository
import com.home.abc.domain.usecase.FilterItemsUseCase
import com.home.abc.domain.usecase.GetTopCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val pagesRepository: PagesRepository,
    private val filterItemsUseCase: FilterItemsUseCase,
    private val topCharactersUseCase: GetTopCharactersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        MainState(
            pages = emptyList(),
            selectedPage = INITIAL_PAGE_INDEX,
            query = "",
            filteredItems = emptyList(),
            isStatsVisible = false,
            stats = null
        )
    )
    val state: StateFlow<MainState> = _state

    init {
        val pages = pagesRepository.getPages()
        val initialItems = pages.firstOrNull()?.items.orEmpty()
        val filtered = filterItemsUseCase(initialItems, "")
        _state.update { current ->
            current.copy(
                pages = pages,
                selectedPage = INITIAL_PAGE_INDEX,
                filteredItems = filtered
            )
        }
    }

    fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.PageChanged -> _state.update { current ->
                val newIndex = if (current.pages.isEmpty()) {
                    INITIAL_PAGE_INDEX
                } else {
                    intent.index.coerceIn(current.pages.indices)
                }
                val filtered = filteredItems(current.pages, newIndex, current.query)
                current.copy(
                    selectedPage = newIndex,
                    filteredItems = filtered,
                    stats = if (current.isStatsVisible) {
                        buildStats(
                            pages = current.pages,
                            query = current.query,
                            currentPageItems = filtered
                        )
                    } else {
                        current.stats
                    }
                )
            }

            is MainIntent.QueryChanged -> _state.update { current ->
                val filtered = filteredItems(current.pages, current.selectedPage, intent.value)
                current.copy(
                    query = intent.value,
                    filteredItems = filtered,
                    stats = if (current.isStatsVisible) {
                        buildStats(
                            pages = current.pages,
                            query = intent.value,
                            currentPageItems = filtered
                        )
                    } else {
                        current.stats
                    }
                )
            }

            MainIntent.ShowStats -> _state.update { current ->
                current.copy(
                    isStatsVisible = true,
                    stats = buildStats(
                        pages = current.pages,
                        query = current.query,
                        currentPageItems = current.filteredItems
                    )
                )
            }

            MainIntent.HideStats -> _state.update { current ->
                current.copy(isStatsVisible = false)
            }
        }
    }

    private fun filteredItems(pages: List<Page>, pageIndex: Int, query: String) =
        filterItemsUseCase(pages.getOrNull(pageIndex)?.items.orEmpty(), query)

    private fun buildStats(
        pages: List<Page>,
        query: String,
        currentPageItems: List<ListItem>
    ): StaticState {
        val pageCounts = pages.mapIndexed { index, page ->
            PageCount(pageIndex = index, count = filterItemsUseCase(page.items, query).size)
        }
        val topChars = topCharactersUseCase(currentPageItems)
        return StaticState(pageCounts = pageCounts, topChars = topChars)
    }
}
