package com.binar.movieapp.util

import com.binar.movieapp.data.network.model.popular.Popular
import com.binar.movieapp.data.network.model.popular.PopularItem

object DummyData {

    fun generateDummyPopularMovies(): Popular {
        val dummyPopularList = ArrayList<PopularItem>()
        repeat(10) {
            val popular = PopularItem(
                title = "Avatar"
            )
            dummyPopularList.add(popular)
        }
        return Popular(results = dummyPopularList)
    }
}