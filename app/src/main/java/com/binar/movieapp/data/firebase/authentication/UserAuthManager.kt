package com.binar.movieapp.data.firebase.authentication

import android.content.Context
import android.util.Log
import com.binar.movieapp.data.firebase.model.User
import com.binar.movieapp.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class UserAuthManager(val context: Context) {
    val auth = FirebaseAuth.getInstance()
    val firestore =  FirebaseFirestore.getInstance()

    fun createUserWithEmailAndPassword(username: String, email: String, password: String) {
        Log.d("user", "running with $username and $password")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result.user!!
                    val user = User(
                        firebaseUser.uid,
                        username = username,
                        email = email,
                    )
                    Log.d("user", user.toString())
                    registerUser(user)
                    Utils.showFailureToast(context, "Register success", false)
                }
            }
            .addOnFailureListener {
                Utils.showFailureToast(context, it.message.toString(), true)
                Log.d("user", "failure: ${it.message}")
            }
    }
/*

    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        var isLoginSuccess = false
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //getUserDetail()
                    isLoginSuccess = true
                } else {

                }
            }
            .addOnFailureListener {
            }
        return isLoginSuccess
    }
*/

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""

        currentUser?.let {
            currentUserId = it.uid
        }

        return currentUserId
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

    fun getUserDetail() {
        firestore.collection(USER)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)!!
/*
                when (fragment) {
                    is LoginFragment -> {
                        fragment.navigateToHome(user)
                    }
                    is ProfileFragment -> {
                        fragment.bindDataToView(user)
                    }
                }*/
            }
            .addOnFailureListener {

            }
    }

    companion object {
        private const val USER = "user"
    }
}