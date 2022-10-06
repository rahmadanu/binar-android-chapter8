package com.binar.movieapp.data.model

import com.binar.movieapp.data.model.popular.PopularItem
import com.binar.movieapp.data.model.toprated.TopRatedItem
import com.google.gson.annotations.SerializedName

sealed class HomeRecyclerViewItem {

    data class Title(
        val id: Int,
        val title: String
    ): HomeRecyclerViewItem()

    data class Popular(
        @SerializedName("page")
        val page: Int? = null,
        @SerializedName("results")
        val results: ArrayList<PopularItem>? = null
    ): HomeRecyclerViewItem()

    data class TopRated(
        @SerializedName("page")
        val page: Int? = null,
        @SerializedName("results")
        val results: List<TopRatedItem?>? = null
    ): HomeRecyclerViewItem()
}