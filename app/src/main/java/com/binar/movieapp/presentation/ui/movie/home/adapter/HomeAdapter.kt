package com.binar.movieapp.presentation.ui.movie.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.movieapp.R
import com.binar.movieapp.data.model.HomeRecyclerViewItem
import com.binar.movieapp.data.model.popular.PopularItem
import com.binar.movieapp.data.model.toprated.TopRatedItem
import com.binar.movieapp.databinding.ItemPopularMovieBinding
import com.binar.movieapp.databinding.ItemTitleBinding
import com.binar.movieapp.databinding.ItemTopRatedMovieBinding

class HomeAdapter: RecyclerView.Adapter<HomeRecyclerViewHolder>() {

    /*private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }*/

    var itemClickListener: ((view: View, item: HomeRecyclerViewItem, position: Int) -> Unit)? = null
/*
    private val diffCallback = object : DiffUtil.ItemCallback<HomeRecyclerViewItem>() {
        override fun areItemsTheSame(
            oldItem: HomeRecyclerViewItem,
            newItem: HomeRecyclerViewItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: HomeRecyclerViewItem,
            newItem: HomeRecyclerViewItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setList(movie: List<HomeRecyclerViewItem>?) {
        differ.submitList(movie)
    }*/

    var items = listOf<HomeRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return when (viewType) {
            R.layout.item_title -> HomeRecyclerViewHolder.TitleViewHolder(
                ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            R.layout.item_popular_movie -> HomeRecyclerViewHolder.PopularViewHolder(
                ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            R.layout.item_top_rated_movie -> HomeRecyclerViewHolder.TopRatedViewHolder(
                ItemTopRatedMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalArgumentException("Invalid viewType provided")
        }
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener
        when (holder) {
            is HomeRecyclerViewHolder.TopRatedViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.TopRated, position)
            is HomeRecyclerViewHolder.PopularViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Popular, position)
            is HomeRecyclerViewHolder.TitleViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Title)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeRecyclerViewItem.Title -> R.layout.item_title
            is HomeRecyclerViewItem.Popular -> R.layout.item_popular_movie
            is HomeRecyclerViewItem.TopRated -> R.layout.item_top_rated_movie
        }
    }
/*
    interface OnItemClickCallBack {
        fun onItemClicked(data: PopularItem)
    }*/
}