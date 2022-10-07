package com.binar.movieapp.presentation.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.network.model.detail.DetailMovie
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository): ViewModel() {

    private val _detailResult = MutableLiveData<Resource<DetailMovie>>()
    val detailResult: LiveData<Resource<DetailMovie>> get() = _detailResult

    fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getDetail(id)
            viewModelScope.launch(Dispatchers.Main) {
                _detailResult.postValue(data)
            }
        }
    }
}