package com.home.abc.domain.model

sealed interface ImageSource {
    data class Remote(val url: String) : ImageSource
    data class Local(val resId: Int) : ImageSource
}

data class Page(
    val id: Int,
    val image: ImageSource,
    val items: List<ListItem>
)

data class ListItem(
    val id: Int,
    val title: String,
    val subtitle: String
)

data class CharCount(
    val char: Char,
    val count: Int
)
