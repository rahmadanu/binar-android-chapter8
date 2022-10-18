package com.binar.movieapp.data.repository

import com.binar.movieapp.data.local.preference.UserPreferenceDataSource
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun setUser(id: Int, name: String, email: String, password: String)
    suspend fun updateUser(user: UserPreferences)
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setProfileImage(image: String)

    fun getUser(): Flow<UserPreferences>
    fun getUserLogin(): Flow<Boolean>
    fun getUserProfileImage(): Flow<String>
}

class UserRepositoryImpl(
    private val userPreferenceDataSource: UserPreferenceDataSource,
): UserRepository {
    override suspend fun setUser(id: Int, name: String, email: String, password: String) {
        userPreferenceDataSource.setUser(id, name, email, password)
    }

    override suspend fun updateUser(user: UserPreferences) {
        userPreferenceDataSource.updateUser(user)
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        userPreferenceDataSource.setUserLogin(isLogin)
    }

    override suspend fun setProfileImage(image: String) {
        userPreferenceDataSource.setProfileImage(image)
    }

    override fun getUser(): Flow<UserPreferences> {
        return userPreferenceDataSource.getUser()
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userPreferenceDataSource.getUserLogin()
    }

    override fun getUserProfileImage(): Flow<String> {
        return userPreferenceDataSource.getUserProfileImage()
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}