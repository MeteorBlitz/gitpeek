package com.example.gitpeek.ui.details.mvi

sealed class DetailIntent {
    data class LoadUserDetails(val username: String) : DetailIntent()
}