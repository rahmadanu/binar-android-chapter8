package com.binar.movieapp.di

import com.binar.movieapp.data.network.datasource.MovieRemoteDataSource
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}