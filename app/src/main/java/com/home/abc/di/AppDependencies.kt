package com.home.abc.di

import com.home.abc.data.datasource.DemoDataSource
import com.home.abc.data.repository.PagesRepositoryImpl
import com.home.abc.domain.repository.PagesRepository
import com.home.abc.domain.usecase.FilterItemsUseCase
import com.home.abc.domain.usecase.GetTopCharactersUseCase

object AppDependencies {
    val pagesRepository: PagesRepository by lazy {
        PagesRepositoryImpl(DemoDataSource)
    }

    val filterItemsUseCase: FilterItemsUseCase by lazy { FilterItemsUseCase() }
    val topCharactersUseCase: GetTopCharactersUseCase by lazy { GetTopCharactersUseCase() }
}
