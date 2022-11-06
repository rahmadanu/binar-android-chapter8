package com.binar.movieapp.util

import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.network.model.HomeMovie
import com.binar.movieapp.data.network.model.HomeMovieItem
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DummyData {

    fun generateDummyMovies(): List<HomeMovie> {
        val dummyPopularList = mutableListOf(HomeMovieItem(
            title = "Doraemon"
        ))
        val dummyTopRatedList = mutableListOf(HomeMovieItem(
            title = "Doraemon"
        ))
        val dummyUpcomingList = mutableListOf(HomeMovieItem(
            title = "Doraemon"
        ))

        val homeMovieList = mutableListOf<HomeMovie>()
        homeMovieList.add(HomeMovie(title = "Popular", results = dummyPopularList))
        homeMovieList.add(HomeMovie(title = "Top Rated", results = dummyTopRatedList))
        homeMovieList.add(HomeMovie(title = "Upcoming", results = dummyUpcomingList))
        return homeMovieList
    }
}