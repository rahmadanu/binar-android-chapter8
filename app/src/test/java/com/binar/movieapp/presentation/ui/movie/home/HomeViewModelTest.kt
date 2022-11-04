package com.binar.movieapp.presentation.ui.movie.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.binar.movieapp.data.network.model.HomeMovie
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.data.repository.UserRepository
import com.binar.movieapp.util.getOrAwaitValue
import com.binar.movieapp.wrapper.Resource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieRepository = mockk()
        userRepository = mockk()
        viewModel = HomeViewModel(movieRepository, userRepository)
    }

    @Test
    fun getHomeMovieListResult() {
        val expectedResult = mockk<LiveData<Resource<List<HomeMovie>>>>()

        every {
            viewModel.homeMovieListResult
        } returns expectedResult

        val actualResult = viewModel.homeMovieListResult.getOrAwaitValue()
        assertEquals(expectedResult, actualResult)
    }
}