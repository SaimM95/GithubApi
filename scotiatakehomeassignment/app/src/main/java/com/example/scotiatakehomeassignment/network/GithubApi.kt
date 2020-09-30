package com.example.scotiatakehomeassignment.network

import com.example.scotiatakehomeassignment.model.Repository
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{username}/repos")
    fun getRepositories(@Path("username") username: String): Deferred<List<Repository>>
}