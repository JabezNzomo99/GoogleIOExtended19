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

class MainActivityViewModel(private val movieRepository: MovieRepository):ViewModel(){

    val trendingMoviesToday = liveData(Dispatchers.IO){
            emitSource(movieRepository.getTrendingMoviesToday())
    }
    val trendingMoviesThisWeek = liveData {
        withContext(Dispatchers.IO){
            emitSource(movieRepository.getTrendingMoviesThisWeek())

        }
    }
    val actionMovies = liveData(Dispatchers.IO){
        emitSource(movieRepository.discoverActionMovies())
    }

    val comedyMovies = liveData(Dispatchers.IO){
            emitSource(movieRepository.discoverComedyMovies())
    }

    val allCategory = MutableLiveData<ArrayList<Category>>()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}