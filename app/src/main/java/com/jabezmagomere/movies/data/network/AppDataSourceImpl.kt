package com.jabezmagomere.movies.data.network

import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.data.models.Response
import com.jabezmagomere.movies.data.network.Api.DiscoverMoviesApiService
import com.jabezmagomere.movies.data.network.Api.MoviesApiService

class AppDataSourceImpl(private val moviesApiService: MoviesApiService, private val discoverMoviesApiService: DiscoverMoviesApiService): AppDataSource {
    override suspend fun fetchActionMovies() = discoverMoviesApiService.fetchActionMovies()
    override suspend fun fetchComedyMovies() = discoverMoviesApiService.fetchComedyMovies()
    override suspend fun fetchTrendingMoviesThisWeek()=moviesApiService.fetchTrendingMoviesThisWeek()
    override suspend fun fetchTrendingMoviesToday()= moviesApiService.fetchTrendingMoviesToday()
}