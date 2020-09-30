package com.example.scotiatakehomeassignment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.scotiatakehomeassignment.network.GithubRepository
import com.example.scotiatakehomeassignment.network.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import timber.log.Timber

class SearchViewModel : ViewModel() {

    fun getRepositories(username: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.loading())
            val repositories = GithubRepository.getRepositories(username).await()

            if (repositories.isEmpty()) {
                emit(Resource.empty())
            } else {
                emit(Resource.success(repositories))
            }
        } catch (exception: HttpException) {
            Timber.e(exception)
            emit(Resource.error())
        }
    }
}