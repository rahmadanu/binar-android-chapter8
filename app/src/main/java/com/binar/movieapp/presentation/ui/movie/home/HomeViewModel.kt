package com.binar.movieapp.presentation.ui.movie.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.model.popular.Popular
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository): ViewModel() {

    private val _getPopularResult = MutableLiveData<Resource<Popular>>()
    val getPopularResult: LiveData<Resource<Popular>> get() = _getPopularResult

    fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getPopular()
            viewModelScope.launch(Dispatchers.Main) {
                _getPopularResult.postValue(data)
            }
        }
    }
}