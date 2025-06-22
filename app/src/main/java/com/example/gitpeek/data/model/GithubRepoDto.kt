package com.example.gitpeek.data.model

data class GithubRepoDto(
    val name: String,
    val description: String?,
    val updated_at: String,
    val created_at: String
)
fun GithubRepoDto.toDomain(): GithubRepo {
    return GithubRepo(
        name = name,
        description = description,
        updatedAt = updated_at,
        createdAt = created_at
    )
}