package com.home.abc.presentation.main

import com.home.abc.domain.model.CharCount
import com.home.abc.domain.model.ListItem
import com.home.abc.domain.model.Page

const val INITIAL_PAGE_INDEX = 0

data class MainState(
    val pages: List<Page> = emptyList(),
    val selectedPage: Int = INITIAL_PAGE_INDEX,
    val query: String = "",
    val filteredItems: List<ListItem> = emptyList(),
    val isStatsVisible: Boolean = false,
    val stats: StaticState? = null
)

data class StaticState(
    val pageCounts: List<PageCount>,
    val topChars: List<CharCount>
)

data class PageCount(
    val pageIndex: Int,
    val count: Int
)
