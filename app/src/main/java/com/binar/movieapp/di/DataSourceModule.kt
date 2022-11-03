package com.binar.movieapp.di

import com.binar.movieapp.data.firebase.datasource.UserRemoteDataSource
import com.binar.movieapp.data.firebase.datasource.UserRemoteDataSourceImpl
import com.binar.movieapp.data.local.datasource.UserLocalDataSource
import com.binar.movieapp.data.local.datasource.UserLocalDataSourceImpl
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
    abstract fun provideMovieRemoteDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    abstract fun provideUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    abstract fun provideUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource
}