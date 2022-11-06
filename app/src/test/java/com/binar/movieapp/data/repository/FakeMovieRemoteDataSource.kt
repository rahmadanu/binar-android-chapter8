package com.binar.movieapp.data.repository

import com.binar.movieapp.data.network.datasource.MovieRemoteDataSource
import com.binar.movieapp.data.network.model.detail.DetailMovie
import com.binar.movieapp.data.network.model.popular.Popular
import com.binar.movieapp.data.network.model.search.Search
import com.binar.movieapp.data.network.model.toprated.TopRated
import com.binar.movieapp.data.network.model.upcoming.Upcoming
import com.binar.movieapp.data.network.service.MovieApiService

class FakeMovieRemoteDataSource(private val apiService: MovieApiService): MovieRemoteDataSource {
    override suspend fun getPopular(): Popular {
        return apiService.getPopular()
    }

    override suspend fun getTopRated(): TopRated {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcoming(): Upcoming {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(query: String): Search {
        return apiService.searchMovie(query = query)
    }

    override suspend fun getDetail(id: Int): DetailMovie {
        TODO("Not yet implemented")
    }
}