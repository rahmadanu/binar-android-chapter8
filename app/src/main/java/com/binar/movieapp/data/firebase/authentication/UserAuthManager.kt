package com.binar.movieapp.data.firebase.authentication

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.room.Update
import com.binar.movieapp.data.firebase.model.User
import com.binar.movieapp.presentation.ui.movie.home.HomeFragment
import com.binar.movieapp.presentation.ui.user.login.LoginFragment
import com.binar.movieapp.presentation.ui.user.profile.ProfileFragment
import com.binar.movieapp.presentation.ui.user.profile.UpdateProfileFragment
import com.binar.movieapp.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
class UserAuthManager(val context: Context) {
    val auth = FirebaseAuth.getInstance()
    val firestore =  FirebaseFirestore.getInstance()
    private var isLoginSuccess = false

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

    suspend fun signInWithEmailAndPassword(email: String, password: String) {
        Log.d("signin", isLoginSuccess.toString())

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                isLoginSuccess = it.isSuccessful
                Utils.showFailureToast(context, "Login success", false)
            }
            .addOnFailureListener {
                isLoginSuccess = false
                Log.d("signin", it.message.toString())
                Utils.showFailureToast(context, it.message.toString(), true)
            }
        Log.d("signin", isLoginSuccess.toString())
    }

    @JvmName("isLoginSuccess1")
    fun isLoginSuccess(): Boolean {
      return isLoginSuccess
    }

    private fun getCurrentUserId(): String {
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

    fun getUserDetail(fragment: Fragment) {
        firestore.collection(USER)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)!!

                when (fragment) {
                    is LoginFragment -> {
                        fragment.navigateToHome(user)
                    }
                    is ProfileFragment -> {
                        fragment.bindDataToView(user)
                    }
                    is HomeFragment -> {
                        fragment.getInitialUser(user)
                    }
                }
            }
            .addOnFailureListener {
                Log.e(fragment.javaClass.simpleName, "Error while getting user detail")
            }
    }

    fun updateUserProfile(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        firestore.collection(USER)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                when (fragment) {
                    is UpdateProfileFragment -> {
                        fragment.activity?.finish()
                    }
                }
            }

            .addOnFailureListener {
                when (fragment) {
                    is UpdateProfileFragment -> {
                        fragment.activity?.finish()
                    }
                }
                Log.e(fragment.javaClass.simpleName, "Error while updating the user details")
            }
    }

    companion object {
        private const val USER = "user"
    }
}

