package com.binar.movieapp.presentation.ui.movie.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.model.HomeRecyclerViewItem
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository): ViewModel() {

    /*private val _getPopularResult = MutableLiveData<Resource<List<HomeRecyclerViewItem>>>()
    val getPopularResult: LiveData<Resource<List<HomeRecyclerViewItem>>> get() = _getPopularResult
*/
    private val _homeItemListResult = MutableLiveData<Resource<List<HomeRecyclerViewItem>>>()
    val homeItemListResult: LiveData<Resource<List<HomeRecyclerViewItem>>> get() = _homeItemListResult

   /* init {
        getHomeListItems()
    }*/

    fun getHomeListItems() =
       viewModelScope.launch(Dispatchers.IO) {
           _homeItemListResult.postValue(Resource.Loading())
           val popularDeferred = async { repository.getPopular() }
           val topRatedDeferred = async { repository.getTopRated() }

           val popular = popularDeferred.await()
           val topRated = topRatedDeferred.await()

           val homeItemList = mutableListOf<HomeRecyclerViewItem>()
           if (popular is Resource.Success && topRated is Resource.Success) {
               homeItemList.add(HomeRecyclerViewItem.Title(1, "Popular"))
               homeItemList.add(HomeRecyclerViewItem.Popular(1, results = popular.payload?.results))
               homeItemList.add(HomeRecyclerViewItem.Title(2, "Top Rated"))
               homeItemList.add(HomeRecyclerViewItem.TopRated(1, results = topRated.payload?.results))
               _homeItemListResult.postValue(Resource.Success(homeItemList))
           }
    }

    /* fun getPopular() {
         viewModelScope.launch(Dispatchers.IO) {
             val data = repository.getPopular()
             viewModelScope.launch(Dispatchers.Main) {
                 _getPopularResult.postValue(data)
             }
         }
     }*/
}