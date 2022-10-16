package com.binar.movieapp.presentation.ui.movie.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.movieapp.data.network.model.search.SearchItem
import com.binar.movieapp.databinding.ItemSearchMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SearchAdapter(private val itemClick: (SearchItem) -> Unit) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(movie: List<SearchItem>?) {
        differ.submitList(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class SearchViewHolder(private val binding: ItemSearchMovieBinding, private val itemClick: (SearchItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItem) {
            with(binding) {
                with(item) {
                    Glide.with(itemView)
                        .load(IMAGE_URL + posterPath)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivPoster)
                    tvTitle.text = title

                    itemView.setOnClickListener {
                        itemClick(this)
                    }
                }
            }
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}