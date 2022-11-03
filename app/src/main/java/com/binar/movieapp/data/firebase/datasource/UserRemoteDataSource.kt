package com.binar.movieapp.data.firebase.datasource

import com.binar.movieapp.data.firebase.firestore.UserFirestoreManager
import com.binar.movieapp.data.local.preference.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun createUserWithEmailAndPassword(username: String, email: String, password: String)
}

class UserRemoteDataSourceImpl @Inject constructor(
    private val userFirestoreManager: UserFirestoreManager
): UserRemoteDataSource {
    override suspend fun createUserWithEmailAndPassword(username: String, email: String, password: String) {
        userFirestoreManager.createUserWithEmailAndPassword(username, email, password)
    }

}