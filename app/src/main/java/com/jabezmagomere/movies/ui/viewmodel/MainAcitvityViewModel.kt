package com.jabezmagomere.movies.ui.viewmodel

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jabezmagomere.movies.data.models.Movie
import com.jabezmagomere.movies.data.repository.MovieRepository
import com.jabezmagomere.movies.ui.BaseViewModel
import com.jabezmagomere.movies.ui.view.Category
import com.jabezmagomere.movies.util.lazyDeferred
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainAcitvityViewModel(private val movieRepository: MovieRepository):BaseViewModel(){

    val trendingMoviesToday = MutableLiveData<List<Movie>>()
    val trendingMoviesThisWeek = MutableLiveData<List<Movie>>()
    val allCategory = MutableLiveData<ArrayList<Category>>()
    val exception = MutableLiveData<Exception>()

    fun getTrendingMoviesToday()=launch {
        withContext(Dispatchers.IO){
            try {
                val movies = movieRepository.getTrendingMoviesToday()
                trendingMoviesToday.postValue(movies)
            }catch (ex:Exception){
                exception.postValue(ex)
            }
        }
    }
    fun getTrendingMoviesThisWeek()= launch {
        withContext(Dispatchers.IO){
            try {
                trendingMoviesThisWeek.postValue(movieRepository.getTrendingMoviesThisWeek())
            }catch (ex:Exception){
                exception.postValue(ex)
            }
        }
    }
}