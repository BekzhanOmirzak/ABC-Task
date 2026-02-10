package com.home.abc.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.abc.domain.repository.PagesRepository
import com.home.abc.domain.usecase.FilterItemsUseCase
import com.home.abc.domain.usecase.GetTopCharactersUseCase

class MainViewModelFactory(
    private val pagesRepository: PagesRepository,
    private val filterItemsUseCase: FilterItemsUseCase,
    private val topCharactersUseCase: GetTopCharactersUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pagesRepository, filterItemsUseCase, topCharactersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
