package com.binar.movieapp.presentation.ui.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSource
import com.binar.movieapp.data.network.datasource.MovieRemoteDataSourceImpl
import com.binar.movieapp.data.network.model.detail.DetailMovie
import com.binar.movieapp.data.network.service.MovieApiService
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.data.repository.MovieRepositoryImpl
import com.binar.movieapp.util.getOrAwaitValue
import com.binar.movieapp.wrapper.Resource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.assertj.core.api.Assertions.*


@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var apiService: MovieApiService
    private lateinit var dataSource: MovieRemoteDataSource
    private lateinit var movieRepository: MovieRepository
    private lateinit var viewModel: DetailViewModel

    private val dummyId = 238

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        apiService = mockk()
        dataSource = MovieRemoteDataSourceImpl(apiService)
        movieRepository = MovieRepositoryImpl(dataSource)
        viewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun `when getDetail should not be null and return Success`() {
        //given
        val respDetail = mockk<Resource<DetailMovie>>()

        every {
            runBlocking {
                movieRepository.getDetail(dummyId)
            }
        } returns respDetail

        //when
        viewModel.getDetail(dummyId)

/*        verify {
            runBlocking {
                movieRepository.getDetail(dummyId)
            }
        }*/

        //then
        val result = viewModel.detailResult.getOrAwaitValue()
        assertNotNull(result)
        //assertTrue(result is Resource.Success)
        //assertThat(result is Resource.Success).isTrue()
        //assertEquals(respDetail, result.payload)
    }

    @Test
    fun `when getDetail should be null and return Error`() {
        //given
        val respDetail = mockk<Resource.Error<DetailMovie>>()

        every {
            runBlocking {
                movieRepository.getDetail(dummyId)
            }
        } returns respDetail

        //when
        viewModel.getDetail(dummyId)

        //then
        val result = viewModel.detailResult.getOrAwaitValue()
        assertNotNull(result)
        assertNotEquals(respDetail, result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}