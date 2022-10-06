package com.binar.movieapp.data.model.toprated

import com.binar.movieapp.data.model.HomeRecyclerViewItem
import com.google.gson.annotations.SerializedName

data class TopRated(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<TopRatedItem>? = null
)
