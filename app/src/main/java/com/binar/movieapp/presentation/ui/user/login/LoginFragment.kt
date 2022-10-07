package com.binar.movieapp.presentation.ui.user.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.binar.movieapp.R
import com.binar.movieapp.data.local.model.user.UserEntity
import com.binar.movieapp.databinding.FragmentLoginBinding
import com.binar.movieapp.di.UserServiceLocator
import com.binar.movieapp.presentation.ui.movie.HomeActivity
import com.binar.movieapp.util.viewModelFactory
import com.binar.movieapp.wrapper.Resource

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModelFactory {
        LoginViewModel(UserServiceLocator.provideUserRepository(requireContext()))
    }

    private var args: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener { checkLogin() }

        if (isUserLoggedIn()) {
            navigateToHome()
        }

        binding.tvRegisterHere.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkLogin() {
        if (validateInput()) {
            val username = binding.etUsername.text.toString()
            viewModel.getUser(username)

            viewModel.getUserResult.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> checkUser(it.payload)
                    is Resource.Error -> Toast.makeText(
                        requireContext(),
                        it.exception?.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> {}
                }
            }
        }
    }

    private fun checkUser(user: UserEntity?) {
        if (validateInput()) {
            if (user != null) {
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()

                val userLoggedIn = username == user.username && password == user.password
                args = user.username

                if (userLoggedIn) {
                    navigateToHome()
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show()
                }
                viewModel.setIfUserLogin(userLoggedIn)
            } else {
                Toast.makeText(context, "Username not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return viewModel.checkIfUserLoggedIn()
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = "Username must not be empty"
        }
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Password must not be empty", Toast.LENGTH_SHORT)
                .show()
        }
        return isValid
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java).apply {
            putExtra(EXTRA_USERNAME, args)
        }
        startActivity(intent)
        activity?.finish()
/*        val option = NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment, true)
            .build()
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(args)
        findNavController().navigate(action, option)*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}