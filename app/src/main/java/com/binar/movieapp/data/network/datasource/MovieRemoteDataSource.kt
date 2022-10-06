package com.binar.movieapp.data.network.datasource

import com.binar.movieapp.data.model.popular.Popular
import com.binar.movieapp.data.model.search.Search
import com.binar.movieapp.data.model.toprated.TopRated
import com.binar.movieapp.data.network.service.MovieApiService

interface MovieRemoteDataSource {
    suspend fun getPopular(): Popular
    suspend fun getTopRated(): TopRated
    suspend fun searchMovie(query: String): Search
}

class MovieRemoteDataSourceImpl(private val apiService: MovieApiService): MovieRemoteDataSource {
    override suspend fun getPopular(): Popular {
        return apiService.getPopular()
    }

    override suspend fun getTopRated(): TopRated {
        return apiService.getTopRated()
    }

    override suspend fun searchMovie(query: String): Search {
        return apiService.searchMovie(query = query)
    }

}