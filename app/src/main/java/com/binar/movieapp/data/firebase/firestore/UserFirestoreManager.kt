package com.binar.movieapp.data.firebase.firestore

import android.content.Context
import android.util.Log
import com.binar.movieapp.data.firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserFirestoreManager @Inject constructor(@ApplicationContext private val context: Context){

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun createUserWithEmailAndPassword(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result.user!!
                    val user = User(
                        firebaseUser.uid,
                        username = username,
                        email = email,
                        password = password
                    )
                    registerUser(user)
                }
            }
    }

    private fun registerUser(user: User) {
        firestore.collection(USER)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                auth.signOut()

            }
            .addOnFailureListener {
            }
    }

    companion object {
        private const val USER = "user"
    }
}