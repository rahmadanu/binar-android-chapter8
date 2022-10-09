package com.binar.movieapp.presentation.ui.user.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.local.model.user.UserEntity
import com.binar.movieapp.data.repository.UserRepository
import com.binar.movieapp.util.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private var _getIfUserExistResult = SingleLiveEvent<Boolean>()
    val getIfUserExistResult: SingleLiveEvent<Boolean> get() = _getIfUserExistResult

    fun registerUser(user: UserEntity) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    fun getIfUserExist(username: String){
        viewModelScope.launch {
            _getIfUserExistResult.value = repository.getIfUserExists(username)
        }
    }
}