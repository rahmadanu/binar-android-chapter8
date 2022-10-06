package com.binar.movieapp.data.repository

import com.binar.movieapp.data.model.HomeRecyclerViewItem
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSource
import com.binar.movieapp.wrapper.Resource

interface MovieRepository {
    suspend fun getPopular(): Resource<HomeRecyclerViewItem.Popular>
    suspend fun getTopRated(): Resource<HomeRecyclerViewItem.TopRated>
}

class MovieRepositoryImpl(private val dataSource: MovieRemoteDataSource): MovieRepository {
    override suspend fun getPopular(): Resource<HomeRecyclerViewItem.Popular> {
        return proceed {
            dataSource.getPopular()
        }
    }

    override suspend fun getTopRated(): Resource<HomeRecyclerViewItem.TopRated> {
        return proceed {
            dataSource.getTopRated()
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