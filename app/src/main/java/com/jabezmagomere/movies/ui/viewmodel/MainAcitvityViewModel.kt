package com.jabezmagomere.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.L
import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.data.repository.MovieRepository
import com.jabezmagomere.movies.ui.view.Category
import kotlinx.coroutines.*
import java.lang.Exception

class MainAcitvityViewModel(private val movieRepository: MovieRepository):ViewModel(){

    val trendingMoviesToday = liveData{
        withContext(Dispatchers.IO) {
            isLoading.postValue(true)
            emitSource(movieRepository.getTrendingMoviesToday())
            isLoading.postValue(false)
        }
    }
    val trendingMoviesThisWeek = liveData {
        withContext(Dispatchers.IO){
            isLoading.postValue(true)
            emitSource(movieRepository.getTrendingMoviesThisWeek())
            isLoading.postValue(false)

        }
    }
    val actionMovies = liveData {
        withContext(Dispatchers.IO){
            isLoading.postValue(true)
            emitSource(movieRepository.discoverActionMovies())
            isLoading.postValue(false)
        }
    }
    val comedyMovies = liveData {
        withContext(Dispatchers.IO){
            isLoading.postValue(true)
            emitSource(movieRepository.discoverComedyMovies())
            isLoading.postValue(false)
        }
    }
    val allCategory = MutableLiveData<ArrayList<Category>>()
    val isLoading = MutableLiveData<Boolean>()
}