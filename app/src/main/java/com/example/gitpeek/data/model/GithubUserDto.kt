package com.example.gitpeek.data.model

data class GithubUserDto(
    val login: String,
    val name: String?,
    val avatar_url: String,
    val location: String?,
    val public_repos: Int
)

// Extension to map DTO to domain model
fun GithubUserDto.toDomain() = GithubUser(
    login = login,
    name = name,
    avatarUrl = avatar_url,
    location = location,
    publicRepos = public_repos
)