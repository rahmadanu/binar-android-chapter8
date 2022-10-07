package com.binar.movieapp.presentation.ui.movie.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.movieapp.data.network.model.search.SearchItem
import com.binar.movieapp.databinding.ItemSearchMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }
    private val diffCallback = object : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setList(movie: List<SearchItem?>?) {
        differ.submitList(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindView(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class SearchViewHolder(private val binding: ItemSearchMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: SearchItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(IMAGE_URL + item.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPosterMovie)
                tvTitleMovie.text = item.title
            }
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: SearchItem)
    }
}