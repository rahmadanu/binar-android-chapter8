package com.binar.movieapp.data.network.datasource

import com.binar.movieapp.data.model.HomeRecyclerViewItem
import com.binar.movieapp.data.network.service.MovieApiService

interface MovieRemoteDataSource {
    suspend fun getPopular(): HomeRecyclerViewItem.Popular
    suspend fun getTopRated(): HomeRecyclerViewItem.TopRated
}

class MovieRemoteDataSourceImpl(private val apiService: MovieApiService): MovieRemoteDataSource {
    override suspend fun getPopular(): HomeRecyclerViewItem.Popular {
        return apiService.getPopular()
    }

    override suspend fun getTopRated(): HomeRecyclerViewItem.TopRated {
        return apiService.getTopRated()
    }

}