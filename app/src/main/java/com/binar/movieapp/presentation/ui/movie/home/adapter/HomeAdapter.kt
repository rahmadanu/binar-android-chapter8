package com.binar.movieapp.presentation.ui.movie.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.movieapp.data.model.HomeMovie
import com.binar.movieapp.data.model.HomeRecyclerViewItem
import com.binar.movieapp.data.model.popular.Popular
import com.binar.movieapp.data.model.popular.PopularItem
import com.binar.movieapp.databinding.ItemTitleBinding

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    /*private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }*/

    var itemClickListener: ((view: View, item: HomeRecyclerViewItem, position: Int) -> Unit)? = null

    private val diffCallback = object : DiffUtil.ItemCallback<HomeMovie>() {
        override fun areItemsTheSame(oldItem: HomeMovie, newItem: HomeMovie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: HomeMovie, newItem: HomeMovie): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(movie: List<HomeMovie>?) {
        differ.submitList(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    class HomeViewHolder(private val binding: ItemTitleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeMovie) {
            with(binding) {
                tvTitle.text = item.title

                val posterAdapter = HomePosterAdapter()
                posterAdapter.submitList(item.results)
                rvPoster.adapter = posterAdapter
                rvPoster.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            }
        }
    }
/*
    interface OnItemClickCallBack {
        fun onItemClicked(data: PopularItem)
    }*/
}