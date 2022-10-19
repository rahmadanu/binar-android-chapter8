package com.binar.movieapp.data.local.preference

import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    suspend fun setUser(id: Int, name: String, email: String, password: String)

    suspend fun updateUser(user: UserPreferences)

    suspend fun setUserLogin(isLogin: Boolean)

    suspend fun setProfileImage(image: String)

    fun getUser(): Flow<UserPreferences>

    fun getUserLogin(): Flow<Boolean>
}

class UserPreferenceDataSourceImpl(
    private val userDataStore: UserDataStoreManager
): UserPreferenceDataSource {
    override suspend fun setUser(id: Int, name: String, email: String, password: String) {
        userDataStore.setUser(id, name, email, password)
    }

    override suspend fun updateUser(user: UserPreferences) {
        userDataStore.updateUser(user)
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        userDataStore.setUserLogin(isLogin)
    }

    override suspend fun setProfileImage(image: String) {
        userDataStore.setProfileImage(image)
    }

    override fun getUser(): Flow<UserPreferences> {
        return userDataStore.getUser()
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userDataStore.getUserLogin()
    }
}