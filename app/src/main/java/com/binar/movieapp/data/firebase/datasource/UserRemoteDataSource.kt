package com.binar.movieapp.data.firebase.datasource

import com.binar.movieapp.data.firebase.authentication.UserAuthManager
import javax.inject.Inject

interface UserRemoteDataSource {
    fun createUserWithEmailAndPassword(username: String, email: String, password: String)
    //suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean
    //suspend fun getUserDetail()
}

class UserRemoteDataSourceImpl @Inject constructor(
    private val userAuthManager: UserAuthManager
): UserRemoteDataSource {
    override fun createUserWithEmailAndPassword(username: String, email: String, password: String) {
        userAuthManager.createUserWithEmailAndPassword(username, email, password)
    }

   /* override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): Boolean {
        return userAuthManager.signInWithEmailAndPassword(email, password)
    }*/

    /*override suspend fun getUserDetail() {
        userAuthManager.getUserDetail()
    }*/

}