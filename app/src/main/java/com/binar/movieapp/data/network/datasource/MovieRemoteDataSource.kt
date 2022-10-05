package com.binar.movieapp.data.network.datasource

import com.binar.movieapp.data.network.service.MovieApiService
import com.binar.movieapp.data.model.popular.Popular

interface MovieRemoteDataSource {
    suspend fun getPopular(): Popular
}

class MovieRemoteDataSourceImpl(private val apiService: MovieApiService): MovieRemoteDataSource {
    override suspend fun getPopular(): Popular {
        return apiService.getPopular()
    }

}