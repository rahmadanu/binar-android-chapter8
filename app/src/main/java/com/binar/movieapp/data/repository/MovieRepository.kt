package com.binar.movieapp.data.repository

import com.binar.movieapp.data.model.HomeMovieItem
import com.binar.movieapp.data.model.HomeRecyclerViewItem
import com.binar.movieapp.data.model.popular.Popular
import com.binar.movieapp.data.model.search.Search
import com.binar.movieapp.data.model.toprated.TopRated
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSource
import com.binar.movieapp.wrapper.Resource
import com.google.gson.annotations.SerializedName

interface MovieRepository {
    suspend fun getPopular(): Resource<List<HomeMovieItem>>
    suspend fun getTopRated(): Resource<List<HomeMovieItem>>
    suspend fun searchMovie(query: String): Resource<Search>
}

class MovieRepositoryImpl(private val dataSource: MovieRemoteDataSource): MovieRepository {
    override suspend fun getPopular(): Resource<List<HomeMovieItem>> {
        return proceed {
            dataSource.getPopular().results?.map {
                HomeMovieItem(
                    adult = it.adult,
                    backdropPath = it.backdropPath,
                    genreIds = it.genreIds,
                    id = it.id,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    overview = it.overview,
                    popularity = it.popularity,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    video = it.video,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount
                ) }!!
        }
    }

    override suspend fun getTopRated(): Resource<List<HomeMovieItem>> {
        return proceed {
            dataSource.getTopRated().results?.map {
                HomeMovieItem(
                    adult = it.adult,
                    backdropPath = it.backdropPath,
                    genreIds = it.genreIds,
                    id = it.id,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    overview = it.overview,
                    popularity = it.popularity,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    video = it.video,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount
                ) }!!
        }
    }

    override suspend fun searchMovie(query: String): Resource<Search> {
        return try {
            val data = dataSource.searchMovie(query)
            if (data.results.isNullOrEmpty()) Resource.Empty() else Resource.Success(data)
        } catch (exception: Exception) {
            Resource.Error(exception)
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