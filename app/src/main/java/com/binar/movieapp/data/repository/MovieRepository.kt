package com.binar.movieapp.data.repository

import com.binar.movieapp.data.model.popular.Popular
import com.binar.movieapp.data.network.datasource.MovieDataSource
import com.binar.movieapp.wrapper.Resource

interface MovieRepository {
    suspend fun getPopular(language: String, page: Int): Resource<Popular>
}

class MovieRepositoryImpl(private val dataSource: MovieDataSource): MovieRepository {
    override suspend fun getPopular(language: String, page: Int): Resource<Popular> {
        return proceed {
            dataSource.getPopular(language, page)
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