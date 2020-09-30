package com.example.scotiatakehomeassignment.network

import com.example.scotiatakehomeassignment.BuildConfig
import com.example.scotiatakehomeassignment.model.Repository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubRepository {

    private const val BASE_URL = "https://api.github.com"

    private val githubApi: GithubApi

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(interceptor)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClientBuilder.build())
            .build()

        githubApi = retrofit.create(GithubApi::class.java)
    }

    fun getRepositories(username: String): Deferred<List<Repository>> {
        return githubApi.getRepositories(username)
    }
}