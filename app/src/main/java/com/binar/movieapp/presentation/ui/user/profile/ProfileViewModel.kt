package com.binar.movieapp.presentation.ui.user.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    fun getUser(): LiveData<UserPreferences> {
        return repository.getUser().asLiveData()
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            repository.setUserLogin(isLogin)
        }
    }

    fun updateUser(user: UserPreferences) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }
}