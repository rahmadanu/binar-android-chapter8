package com.binar.movieapp.di

import android.content.Context
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSource
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSourceImpl
import com.binar.movieapp.data.network.service.MovieApiService
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.data.repository.MovieRepositoryImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor

object MovieServiceLocator {

    private fun provideChucker(appContext: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(appContext).build()
    }

    private fun provideApiService(chuckerInterceptor: ChuckerInterceptor): MovieApiService {
        return MovieApiService.invoke(chuckerInterceptor)
    }

    private fun provideDataSource(apiService: MovieApiService): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(apiService)
    }

    fun provideMovieRepository(context: Context): MovieRepository {
        return MovieRepositoryImpl(provideDataSource(provideApiService(provideChucker(context))))
    }
}