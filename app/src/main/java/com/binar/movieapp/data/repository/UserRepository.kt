package com.binar.movieapp.data.repository

import com.binar.movieapp.data.firebase.datasource.UserRemoteDataSource
import com.binar.movieapp.data.local.datasource.UserLocalDataSource
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    fun createUserWithEmailAndPassword(username: String, email: String, password: String)
    //suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean
    //suspend fun getUserDetail()

    suspend fun setUser(user: UserPreferences)
    suspend fun updateUser(user: UserPreferences)
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setProfileImage(image: String)

    fun getUser(): Flow<UserPreferences>
    fun getUserLogin(): Flow<Boolean>
}

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override fun createUserWithEmailAndPassword(
        username: String,
        email: String,
        password: String
    ) {
        userRemoteDataSource.createUserWithEmailAndPassword(username, email, password)
    }

    /*override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ) : Boolean {
        return userRemoteDataSource.signInWithEmailAndPassword(email, password)
    }*/

//    override suspend fun getUserDetail() {
//        userRemoteDataSource.getUserDetail()
//    }

    override suspend fun setUser(user: UserPreferences) {
        userLocalDataSource.setUser(user)
    }

    override suspend fun updateUser(user: UserPreferences) {
        userLocalDataSource.updateUser(user)
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        userLocalDataSource.setUserLogin(isLogin)
    }

    override suspend fun setProfileImage(image: String) {
        userLocalDataSource.setProfileImage(image)
    }

    override fun getUser(): Flow<UserPreferences> {
        return userLocalDataSource.getUser()
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userLocalDataSource.getUserLogin()
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}