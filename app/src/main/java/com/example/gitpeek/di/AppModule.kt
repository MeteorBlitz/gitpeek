package com.example.gitpeek.di

import com.example.gitpeek.data.remote.GithubApi
import com.example.gitpeek.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGithubApi(retrofit: Retrofit): GithubApi =
        retrofit.create(GithubApi::class.java)

    @Provides
    @Singleton
    fun provideGithubRepository(api: GithubApi): GithubRepository =
        GithubRepository(api)
}
