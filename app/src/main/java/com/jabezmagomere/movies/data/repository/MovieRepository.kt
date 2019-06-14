package com.jabezmagomere.movies.data.repository

import androidx.lifecycle.LiveData
import com.jabezmagomere.movies.data.db.Movie

interface MovieRepository {
    suspend fun getTrendingMoviesThisWeek(): LiveData<List<Movie>>
    suspend fun getTrendingMoviesToday(): LiveData<List<Movie>>
    suspend fun discoverActionMovies(): LiveData<List<Movie>>
    suspend fun discoverComedyMovies(): LiveData<List<Movie>>
}