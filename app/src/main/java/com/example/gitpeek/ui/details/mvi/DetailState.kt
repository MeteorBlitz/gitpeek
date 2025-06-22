package com.example.gitpeek.ui.details.mvi

import com.example.gitpeek.data.model.GithubRepo
import com.example.gitpeek.data.model.GithubUser

data class DetailState(
    val isLoading: Boolean = false,
    val user: GithubUser? = null,
    val repos: List<GithubRepo> = emptyList(),
    val error: String? = null
)