package com.binar.movieapp.data.firebase.datasource

import androidx.fragment.app.Fragment
import com.binar.movieapp.data.firebase.authentication.UserAuthManager
import javax.inject.Inject

interface UserRemoteDataSource {
    fun createUserWithEmailAndPassword(username: String, email: String, password: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    fun isLoginSuccess(): Boolean
    fun getUserDetail(fragment: Fragment)
    fun updateUserProfile(fragment: Fragment, userHashMap: HashMap<String, Any>)
}

class UserRemoteDataSourceImpl @Inject constructor(
    private val userAuthManager: UserAuthManager
): UserRemoteDataSource {
    override fun createUserWithEmailAndPassword(username: String, email: String, password: String) {
        userAuthManager.createUserWithEmailAndPassword(username, email, password)
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ){
        return userAuthManager.signInWithEmailAndPassword(email, password)
    }

    override fun isLoginSuccess(): Boolean {
        return userAuthManager.isLoginSuccess()
    }

    override fun getUserDetail(fragment: Fragment) {
        userAuthManager.getUserDetail(fragment)
    }

    override fun updateUserProfile(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        userAuthManager.updateUserProfile(fragment, userHashMap)
    }

}