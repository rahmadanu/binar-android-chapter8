package com.binar.movieapp.di

import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}