package com.binar.movieapp.presentation.ui.user.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(){

    @SuppressLint("Typo")
    fun createUserWithEmailAndPassword(username: String, email: String, password: String) {
            repository.createUserWithEmailAndPassword(username, email, password)
            Log.d("signin", username + password)
    }

   fun signInWithEmailAndPassword(email: String, password: String){
        viewModelScope.launch {
            repository.signInWithEmailAndPassword(email, password)
        }
    }

    fun isLoginSuccess(): Boolean = repository.isLoginSuccess()

    fun getUser(): LiveData<UserPreferences> {
        return repository.getUser().asLiveData()
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            repository.setUserLogin(isLogin)
        }
    }

    fun getUserLogin(): LiveData<Boolean> {
        return repository.getUserLogin().asLiveData()
    }

    fun registerUser(user: UserPreferences) {
        viewModelScope.launch {
            repository.setUser(user)
        }
    }
}