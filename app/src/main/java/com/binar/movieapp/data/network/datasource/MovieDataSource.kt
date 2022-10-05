package com.binar.movieapp.data.network.datasource

import com.binar.movieapp.data.network.service.MovieApiService
import com.binar.movieapp.data.model.popular.Popular
import retrofit2.http.Query

interface MovieDataSource {
    suspend fun getPopular(language: String, page: Int): Popular
}

class MovieDataSourceImpl(private val apiService: MovieApiService): MovieDataSource {
    override suspend fun getPopular(language: String, page: Int): Popular {
        return apiService.getPopular(language, page)
    }

}