package com.binar.movieapp.data.repository

import androidx.fragment.app.Fragment
import com.binar.movieapp.data.firebase.datasource.UserRemoteDataSource
import com.binar.movieapp.data.local.datasource.UserLocalDataSource
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    fun createUserWithEmailAndPassword(username: String, email: String, password: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    fun isLoginSuccess(): Boolean
    fun getUserDetail(fragment: Fragment)
    fun updateUserProfile(fragment: Fragment, userHashMap: HashMap<String, Any>)

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

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ){
        return userRemoteDataSource.signInWithEmailAndPassword(email, password)
    }

    override fun isLoginSuccess(): Boolean {
        return userRemoteDataSource.isLoginSuccess()
    }

    override fun getUserDetail(fragment: Fragment) {
        userRemoteDataSource.getUserDetail(fragment)
    }

    override fun updateUserProfile(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        userRemoteDataSource.updateUserProfile(fragment, userHashMap)
    }

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