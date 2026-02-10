package com.home.abc.domain.usecase

import com.home.abc.domain.model.ListItem

class FilterItemsUseCase {
    operator fun invoke(items: List<ListItem>, query: String): List<ListItem> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return items
        return items.filter { it.title.contains(trimmed, ignoreCase = true) }
    }
}
