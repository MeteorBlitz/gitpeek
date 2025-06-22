package com.example.gitpeek.data.remote

import com.example.gitpeek.data.model.GithubUserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): GithubUserDto
}
