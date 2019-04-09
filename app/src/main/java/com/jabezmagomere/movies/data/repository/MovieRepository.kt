package com.jabezmagomere.movies.data.repository

import com.jabezmagomere.movies.data.models.Movie
import com.jabezmagomere.movies.data.models.Response

interface MovieRepository {
    suspend fun getTrendingMoviesThisWeek(): List<Movie>
    suspend fun getTrendingMoviesToday(): List<Movie>
}