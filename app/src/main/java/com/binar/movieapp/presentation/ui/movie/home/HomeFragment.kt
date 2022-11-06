package com.binar.movieapp.presentation.ui.movie.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.movieapp.data.firebase.model.User
import com.binar.movieapp.databinding.FragmentHomeBinding
import com.binar.movieapp.presentation.ui.movie.home.adapter.HomeAdapter
import com.binar.movieapp.presentation.ui.user.login.LoginFragment
import com.binar.movieapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observeData()
    }

    private fun initList() {
        adapter = HomeAdapter()
        adapter.itemClickListener = {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id!!)
            findNavController().navigate(action)
        }

        binding.apply {
            rvHomeList.layoutManager = LinearLayoutManager(requireContext())
            rvHomeList.adapter = adapter
        }
    }

    fun getInitialUser(user: User) {
        binding.tvUsername.text = user.username
    }

    private fun observeData() {
        viewModel.getUserDetail(this@HomeFragment)

        viewModel.homeMovieListResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setLoadingState(true)
                    setErrorState(false)
                }
                is Resource.Error -> {
                    setLoadingState(true)
                    setErrorState(true, it.exception.toString())
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    setErrorState(false)
                    adapter.submitList(it.payload)
                }
                else -> {}
            }
        }
    }

    private fun setErrorState(isError: Boolean, message: String? = "") {
        binding.tvError.isVisible = isError
        binding.tvError.text = message
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbHomeList.isVisible = isLoading
        binding.rvHomeList.isVisible = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}