package com.jabezmagomere.movies.data.network

import com.jabezmagomere.movies.data.models.Response

class AppDataSourceImpl(private val moviesApiService: MoviesApiService): AppDataSource {
    override suspend fun getTrendingMoviesThisWeek()= moviesApiService.getTrendingMoviesThisWeek().await()
    override suspend fun getTrendingMoviesToday()= moviesApiService.getTrendingMoviesToday().await()
}