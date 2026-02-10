package com.home.abc.domain.repository

import com.home.abc.domain.model.Page

interface PagesRepository {
    fun getPages(): List<Page>
}
