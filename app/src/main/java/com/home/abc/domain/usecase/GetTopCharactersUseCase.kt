package com.home.abc.domain.usecase

import com.home.abc.domain.model.CharCount
import com.home.abc.domain.model.ListItem

class GetTopCharactersUseCase {
    companion object {
        private const val DEFAULT_TOP_CHARACTER_LIMIT = 3
    }

    operator fun invoke(
        items: List<ListItem>,
        limit: Int = DEFAULT_TOP_CHARACTER_LIMIT
    ): List<CharCount> {
        val counts = mutableMapOf<Char, Int>()
        items.forEach { item ->
            item.title.lowercase().forEach { char ->
                if (char.isLetterOrDigit()) {
                    counts[char] = (counts[char] ?: 0) + 1
                }
            }
        }
        return counts.entries
            .sortedWith(compareByDescending<Map.Entry<Char, Int>> { it.value }.thenBy { it.key })
            .take(limit)
            .map { CharCount(it.key, it.value) }
    }
}
