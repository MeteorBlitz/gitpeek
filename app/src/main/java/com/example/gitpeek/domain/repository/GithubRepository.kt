package com.example.gitpeek.domain.repository

import com.example.gitpeek.data.model.GithubUser
import com.example.gitpeek.data.model.toDomain
import com.example.gitpeek.data.remote.GithubApi
import jakarta.inject.Inject

class GithubRepository @Inject constructor(
    private val api: GithubApi
) {
    suspend fun getUser(username: String): Result<GithubUser> = runCatching {
        api.getUser(username).toDomain()
    }
}