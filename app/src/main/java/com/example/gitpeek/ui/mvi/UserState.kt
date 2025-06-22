package com.example.gitpeek.ui.mvi

import com.example.gitpeek.data.model.GithubUser

data class UserState(
    val isLoading: Boolean = false,
    val user: GithubUser? = null,
    val error: String? = null
)
