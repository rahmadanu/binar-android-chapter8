package com.binar.movieapp.presentation.ui.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.binar.movieapp.R
import com.binar.movieapp.data.network.model.detail.DetailMovie
import com.binar.movieapp.databinding.FragmentDetailBinding
import com.binar.movieapp.di.MovieServiceLocator
import com.binar.movieapp.util.viewModelFactory
import com.binar.movieapp.wrapper.Resource
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModelFactory {
        DetailViewModel(MovieServiceLocator.provideMovieRepository(requireContext()))
    }

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        observeData()
    }

    private fun observeData() {
        viewModel.detailResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    setView(it.payload)
                }
            }
        }
    }

    private fun setView(movie: DetailMovie?) {
        movie?.let {
            Glide.with(this)
                .load(IMAGE_URL + movie.backdropPath)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivMovieImage)
            binding.tvMovieTitle.text = movie.title
        }
    }

    private fun getData() {
        viewModel.getDetail(args.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}