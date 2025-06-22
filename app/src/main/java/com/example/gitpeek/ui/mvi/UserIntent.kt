package com.example.gitpeek.ui.mvi

sealed interface UserIntent {
    data class Search(val username: String) : UserIntent
    object Retry : UserIntent
}
