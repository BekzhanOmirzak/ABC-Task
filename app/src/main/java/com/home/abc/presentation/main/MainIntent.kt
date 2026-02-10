package com.home.abc.presentation.main

sealed interface MainIntent {
    data class PageChanged(val index: Int) : MainIntent
    data class QueryChanged(val value: String) : MainIntent
    data object ShowStats : MainIntent
    data object HideStats : MainIntent
}