package com.jabezmagomere.movies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jabezmagomere.movies.data.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(private val movieRepository: MovieRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(movieRepository) as T
    }

}