package com.binar.movieapp.data.repository

import com.binar.movieapp.data.network.model.detail.DetailMovie
import com.binar.movieapp.data.network.model.popular.Popular
import com.binar.movieapp.data.network.model.search.Search
import com.binar.movieapp.data.network.model.toprated.TopRated
import com.binar.movieapp.data.network.model.upcoming.Upcoming
import com.binar.movieapp.data.network.service.MovieApiService
import com.binar.movieapp.util.DummyData

class FakeApiService: MovieApiService {
    private val dummyResponse = DummyData.generateDummyPopularMovies()

    override suspend fun getPopular(language: String, page: Int): Popular {
        return dummyResponse
    }

    override suspend fun getTopRated(language: String, page: Int): TopRated {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcoming(language: String, page: Int): Upcoming {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(
        language: String,
        query: String,
        page: Int,
        include: Boolean
    ): Search {
        return DummyData.generateSearchResponse(query)
    }

    override suspend fun getDetail(id: Int, language: String): DetailMovie {
        TODO("Not yet implemented")
    }
}