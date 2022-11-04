package com.binar.movieapp.di

import android.content.Context
import com.binar.movieapp.data.firebase.authentication.UserAuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideUserAuthManager(@ApplicationContext context: Context): UserAuthManager {
        return UserAuthManager(context)
    }

}