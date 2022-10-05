package com.binar.movieapp.data.repository

import com.binar.movieapp.data.model.popular.Popular
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSource
import com.binar.movieapp.wrapper.Resource

interface MovieRepository {
    suspend fun getPopular(): Resource<Popular>
}

class MovieRepositoryImpl(private val dataSource: MovieRemoteDataSource): MovieRepository {
    override suspend fun getPopular(): Resource<Popular> {
        return proceed {
            dataSource.getPopular()
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}