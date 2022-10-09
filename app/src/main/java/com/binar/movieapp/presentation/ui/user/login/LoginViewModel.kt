package com.binar.movieapp.presentation.ui.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.local.model.user.UserEntity
import com.binar.movieapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel(){

    private var _checkIsUserLoginValid = MutableLiveData<Boolean>()
    val checkIsUserLoginValid: LiveData<Boolean> get() = _checkIsUserLoginValid

    private var _getIfUserExistResult = MutableLiveData<Boolean>()
    val getIfUserExistResult: LiveData<Boolean> get() = _getIfUserExistResult

    private var _userByUsernameResult = MutableLiveData<UserEntity>()
    val userByUsernameResult: LiveData<UserEntity> get() = _userByUsernameResult

    fun checkIsUserLoginValid(username: String, password: String) {
        viewModelScope.launch {
            _checkIsUserLoginValid.postValue(repository.checkIsUserLoginValid(username, password))
        }
    }

    fun getIfUserExist(username: String){
        viewModelScope.launch {
            _getIfUserExistResult.postValue(repository.getIfUserExists(username))
        }
    }

    fun checkIfUserLoggedIn(): Boolean {
        return repository.checkIfUserLoggedIn()
    }

    fun setIfUserLogin(userLoggedIn: Boolean){
        return repository.setIfUserLogin(userLoggedIn)
    }

    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            _userByUsernameResult.postValue(repository.getUserByUsername(username))
        }
    }

    fun setUserId(id: Long) {
        viewModelScope.launch {
            repository.setUserId(id)
        }
    }
}