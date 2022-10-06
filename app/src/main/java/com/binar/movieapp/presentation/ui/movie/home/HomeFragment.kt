package com.binar.movieapp.presentation.ui.movie.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.movieapp.data.model.HomeRecyclerViewItem
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
        viewModel.getHomeListItems()
    }

    private fun setRecyclerView(movie: List<HomeRecyclerViewItem>?) {
        val adapter = HomeAdapter()
        adapter.itemClickListener = { view, item, position ->
            when (item) {
                is HomeRecyclerViewItem.Title -> {
                    Toast.makeText(requireContext(), "Title clicked", Toast.LENGTH_SHORT).show()
                }
                is HomeRecyclerViewItem.Popular -> {
                    Toast.makeText(requireContext(), "Popular movie clicked", Toast.LENGTH_SHORT).show()
                }
                is HomeRecyclerViewItem.TopRated -> {
                    Toast.makeText(requireContext(), "Top rated movie clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
        movie?.let { adapter.items = it }
        binding.apply {
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = adapter
        }
    }

    private fun observeData() {
        viewModel.homeItemListResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    setRecyclerView(it.payload)
                    Log.d("homefragment", it.payload.toString())
                }
            }
        }
        /*viewModel.getPopularResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    setRecyclerView(it.payload)
                    Log.d("homefragment", it.payload.toString())
                }
            }
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}