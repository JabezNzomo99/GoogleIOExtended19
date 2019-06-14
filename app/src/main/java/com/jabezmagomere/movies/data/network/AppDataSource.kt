package com.jabezmagomere.movies.data.network

import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.data.models.Response

interface AppDataSource {
    suspend fun fetchTrendingMoviesThisWeek():retrofit2.Response<Response>
    suspend fun fetchTrendingMoviesToday(): retrofit2.Response<Response>
    suspend fun fetchActionMovies(): retrofit2.Response<Response>
    suspend fun fetchComedyMovies(): retrofit2.Response<Response>
}