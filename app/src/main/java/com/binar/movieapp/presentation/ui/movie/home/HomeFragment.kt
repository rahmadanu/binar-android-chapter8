package com.binar.movieapp.presentation.ui.movie.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.movieapp.R
import com.binar.movieapp.data.network.model.HomeMovie
import com.binar.movieapp.data.network.model.search.Search
import com.binar.movieapp.databinding.FragmentHomeBinding
import com.binar.movieapp.di.MovieServiceLocator
import com.binar.movieapp.presentation.ui.movie.HomeActivity
import com.binar.movieapp.presentation.ui.movie.home.adapter.HomeAdapter
import com.binar.movieapp.presentation.ui.movie.home.adapter.SearchAdapter
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
        setSearchRecyclerView()
        observeData()
    }

    private fun fetchPopular() {
        Log.d("homefragment", "fetching data..")
        viewModel.getHomeMovieList()
    }

    private fun setHomeRecyclerView(movie: List<HomeMovie>?) {
        val adapter = HomeAdapter()
        adapter.submitList(movie)
        adapter.itemClickListener = {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id!!)
            findNavController().navigate(action)
        }

        binding.apply {
            rvHomeList.layoutManager = LinearLayoutManager(requireContext())
            rvHomeList.adapter = adapter
        }
    }

    private fun observeData() {
        viewModel.homeMovieListResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    setHomeRecyclerView(it.payload)
                    Log.d("homefragment", it.payload.toString())
                }
                else -> {}
            }
        }
        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Empty -> {
                    Toast.makeText(requireContext(), "Movie not found", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Log.d("search", it.payload.toString())
                    setSearchRecyclerView(it.payload)
                }
            }
        }
    }

    private fun setSearchRecyclerView(list: Search? = null) {
        val adapter = SearchAdapter()
        Log.d("search", list?.results.toString())
        adapter.setList(list?.results)
        binding.rvSearchList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}