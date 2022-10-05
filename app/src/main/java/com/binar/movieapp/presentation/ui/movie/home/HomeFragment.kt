package com.binar.movieapp.presentation.ui.movie.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.movieapp.R
import com.binar.movieapp.data.model.popular.Popular
import com.binar.movieapp.databinding.FragmentHomeBinding
import com.binar.movieapp.di.MovieServiceLocator
import com.binar.movieapp.presentation.ui.movie.home.adapter.HomeAdapter
import com.binar.movieapp.util.viewModelFactory
import com.binar.movieapp.wrapper.Resource

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModelFactory {
        HomeViewModel(MovieServiceLocator.provideMovieRepository(requireContext()))
    }

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

        fetchPopular()
        observeData()
    }

    private fun fetchPopular() {
        Log.d("homefragment", "fetching data..")
        viewModel.getPopular()
    }

    private fun setRecyclerView(movie: Popular?) {
        val adapter = HomeAdapter()
        adapter.setList(movie?.results)
        binding.apply {
            rvList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvList.adapter = adapter
        }
    }

    private fun observeData() {
        viewModel.getPopularResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    setRecyclerView(it.payload)
                    Log.d("homefragment", it.payload.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}