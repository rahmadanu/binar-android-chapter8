package com.binar.movieapp.presentation.ui.movie.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.movieapp.data.model.popular.Popular
import com.binar.movieapp.data.model.popular.PopularItem
import com.binar.movieapp.databinding.ItemPopularMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    private val diffCallback = object : DiffUtil.ItemCallback<PopularItem>() {
        override fun areItemsTheSame(oldItem: PopularItem, newItem: PopularItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularItem, newItem: PopularItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setList(movie: List<PopularItem>?) {
        differ.submitList(movie)
    }

    inner class HomeViewHolder(private val binding: ItemPopularMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: PopularItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(IMAGE_URL + movie.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPosterImage)
            }
            binding.root.setOnClickListener {
                onItemClickCallBack.onItemClicked(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: PopularItem)
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}