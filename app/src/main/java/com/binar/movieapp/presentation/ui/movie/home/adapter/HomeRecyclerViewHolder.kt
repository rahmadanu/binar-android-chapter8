package com.binar.movieapp.presentation.ui.movie.home.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.binar.movieapp.data.model.HomeRecyclerViewItem
import com.binar.movieapp.data.model.popular.PopularItem
import com.binar.movieapp.data.model.toprated.TopRatedItem
import com.binar.movieapp.databinding.ItemPopularMovieBinding
import com.binar.movieapp.databinding.ItemTitleBinding
import com.binar.movieapp.databinding.ItemTopRatedMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

sealed class HomeRecyclerViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    var itemClickListener: ((view: View, item: HomeRecyclerViewItem, position: Int) -> Unit)? = null

    class TitleViewHolder(private val binding: ItemTitleBinding) : HomeRecyclerViewHolder(binding) {
        fun bind(title: HomeRecyclerViewItem.Title) {
            binding.tvTitle.text = title.title
            binding.tvSeeMore.setOnClickListener {
                itemClickListener?.invoke(it, title, adapterPosition)
            }
        }
    }

    class PopularViewHolder(private val binding: ItemPopularMovieBinding) : HomeRecyclerViewHolder(binding) {
        fun bind(movie: HomeRecyclerViewItem.Popular, position: Int) {
            val posterUrl = movie.results?.get(adapterPosition)
            Log.d("viewholderpopular", posterUrl.toString())
            Glide.with(itemView)
                .load(IMAGE_URL + posterUrl?.posterPath)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivPosterImage)
            binding.root.setOnClickListener {
                itemClickListener?.invoke(it, movie, adapterPosition)
            }
        }
    }

    class TopRatedViewHolder(private val binding: ItemTopRatedMovieBinding): HomeRecyclerViewHolder(binding) {
        fun bind(movie: HomeRecyclerViewItem.TopRated, position: Int) {
            val posterUrl = movie.results?.get(adapterPosition)
            Log.d("viewholdertoprated", IMAGE_URL + posterUrl?.posterPath)
            binding.ivPosterImage.load(IMAGE_URL + posterUrl?.posterPath) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
/*            Glide.with(itemView)
                .load(IMAGE_URL + posterPath)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivPosterImage)*/
            binding.root.setOnClickListener {
                itemClickListener?.invoke(it, movie, adapterPosition)
            }
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}