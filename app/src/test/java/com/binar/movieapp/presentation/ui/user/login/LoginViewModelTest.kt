package com.binar.movieapp.presentation.ui.user.login

import com.binar.movieapp.data.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class LoginViewModelTest {

    private lateinit var repository: UserRepository
    private lateinit var viewModel: LoginViewModel


    @Before
    fun setUp() {
        repository = mockk()
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun getUser() {
    }

    @Test
    fun setUserLogin() {
    }

    @Test
    fun get_userLoggedIn_userLoggedInTrueReturned() {
        //every { repository.getUserLogin() } returns isLoggedIn
    }
}