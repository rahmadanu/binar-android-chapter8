package com.binar.movieapp.presentation.ui.movie.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.network.model.HomeMovie
import com.binar.movieapp.data.network.model.detail.DetailMovie
import com.binar.movieapp.data.network.model.search.Search
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository): ViewModel() {

    private val _searchResult = MutableLiveData<Resource<Search>>()
    val searchResult: LiveData<Resource<Search>> = _searchResult

    private val _homeMovieListResult = MutableLiveData<Resource<List<HomeMovie>>>()
    val homeMovieListResult: LiveData<Resource<List<HomeMovie>>> get() = _homeMovieListResult

    fun getHomeMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            val popular = repository.getPopular()
            val topRated = repository.getTopRated()

            val homeMovieList = mutableListOf<HomeMovie>()
            homeMovieList.add(HomeMovie(title = "Popular cui", results = popular.payload))
            homeMovieList.add(HomeMovie(title = "Top Rated oy", results = topRated.payload))
            viewModelScope.launch(Dispatchers.Main) {
                _homeMovieListResult.postValue(Resource.Success(homeMovieList))
            }
        }
    }

    fun searchMovie(query: String) {
        Log.d("searchMovie", "searchMovie")
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.searchMovie(query)
            viewModelScope.launch(Dispatchers.Main) {
                _searchResult.postValue(data)
            }
        }
    }
}