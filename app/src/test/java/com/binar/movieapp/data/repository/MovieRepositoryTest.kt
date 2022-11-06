package com.binar.movieapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.movieapp.MainDispatcherRule
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSource
import com.binar.movieapp.data.network.model.HomeMovie
import com.binar.movieapp.data.network.model.HomeMovieItem
import com.binar.movieapp.data.network.model.popular.PopularItem
import com.binar.movieapp.data.network.service.MovieApiService
import com.binar.movieapp.util.DummyData
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: MovieApiService
    private lateinit var dataSource: MovieRemoteDataSource
    private lateinit var repository: MovieRepository


    @Before
    fun setUp() {
        apiService = FakeApiService()
        dataSource = FakeMovieRemoteDataSource(apiService)
        repository = MovieRepositoryImpl(dataSource)
    }

    @Test
    fun `when getPopularMovies should not be null`() = runTest {
        val expectedResult = DummyData.generateDummyPopularMovies()
        val expectedResultInNumber = 10
        val actualResult = repository.getPopular()
        Assert.assertNotNull(actualResult)
        Assert.assertEquals(
            expectedResult.results?.size,
            (actualResult as Resource.Success).data.size)
    }

    @Test
    fun `when popular movie should exist in popular movie list`() = runTest {
        val samplePopularMovie: List<HomeMovieItem> = DummyData.generateDummyPopularMovies().results?.map {
            HomeMovieItem(title = it.title)
        }!!
        val sample = HomeMovieItem(
            title = "Avatar"
        )
        val sample2 = HomeMovieItem(
            title = "Minion"
        )
        val actualResult = repository.getPopular() as Resource.Success
        Assert.assertTrue(actualResult.data.contains(samplePopularMovie[0]))
        Assert.assertTrue(actualResult.data.contains(sample))
        Assert.assertFalse(actualResult.data.contains(sample2))
    }


    @Test
    fun `when search query is empty`() = runTest {
        //given
        val query = ""

        //when
        repository.searchMovie(query)

        //then
        val actual = repository.searchMovie(query)
        Assert.assertTrue(actual.payload.isNullOrEmpty())
    }

    @Test
    fun `when a movie popularity is greather than another movie`() = runTest {
        val samplePopularMovie = ArrayList<HomeMovieItem>()
        val sample = HomeMovieItem(
            title = "Avatar1",
            popularity = 1000.0
        )
        val sample2 = HomeMovieItem(
            title = "Avatar2",
            popularity = 2000.0
        )
        samplePopularMovie.add(sample)
        samplePopularMovie.add(sample2)

        Assert.assertTrue(sample2.popularity!! > sample.popularity!!)
    }
}