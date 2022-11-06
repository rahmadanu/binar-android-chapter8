package com.binar.movieapp.presentation.ui.movie.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.data.network.model.HomeMovie
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.data.repository.UserRepository
import com.binar.movieapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository, private val userRepository: UserRepository): ViewModel() {

    private val _homeMovieListResult = MutableLiveData<Resource<List<HomeMovie>>>()
    val homeMovieListResult: LiveData<Resource<List<HomeMovie>>> get() = _homeMovieListResult

    init {
        getHomeMovieList()
    }

    fun getHomeMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeMovieListResult.postValue(Resource.Loading())
            //delay(1000)
            val popular = movieRepository.getPopular()
            val topRated = movieRepository.getTopRated()
            val upcoming = movieRepository.getUpcoming()

            val homeMovieList = mutableListOf<HomeMovie>()
            homeMovieList.add(HomeMovie(title = "Popular", results = popular.payload))
            homeMovieList.add(HomeMovie(title = "Top Rated", results = topRated.payload))
            homeMovieList.add(HomeMovie(title = "Upcoming", results = upcoming.payload))
            viewModelScope.launch(Dispatchers.Main) {
                _homeMovieListResult.postValue(Resource.Success(homeMovieList))
            }
        }
    }

    fun getUserDetail(fragment: Fragment) {
        userRepository.getUserDetail(fragment)
    }

    fun getUser(): LiveData<UserPreferences> {
        return userRepository.getUser().asLiveData()
    }
}