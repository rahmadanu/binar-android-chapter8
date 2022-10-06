package com.binar.movieapp.data.model

import com.binar.movieapp.data.model.popular.PopularItem
import com.binar.movieapp.data.model.toprated.TopRatedItem
import com.google.gson.annotations.SerializedName

class HomeMovie(
    val title: String? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<HomeMovieItem>? = null
)