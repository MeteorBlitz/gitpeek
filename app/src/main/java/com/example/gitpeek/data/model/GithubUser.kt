package com.example.gitpeek.data.model

data class GithubUser(
    val login: String,
    val name: String?,
    val avatarUrl: String,
    val location: String?,
    val publicRepos: Int
)
