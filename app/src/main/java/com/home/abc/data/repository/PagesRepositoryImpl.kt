package com.home.abc.data.repository

import com.home.abc.data.datasource.DemoDataSource
import com.home.abc.domain.model.Page
import com.home.abc.domain.repository.PagesRepository

class PagesRepositoryImpl(
    private val dataSource: DemoDataSource
) : PagesRepository {
    override fun getPages(): List<Page> = dataSource.getPages()
}
