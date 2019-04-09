package com.jabezmagomere.movies.data.repository

import com.jabezmagomere.movies.data.models.Response
import com.jabezmagomere.movies.data.network.AppDataSource

class MovieRepositoryImpl(private val appDataSource: AppDataSource) : MovieRepository {
    override suspend fun getTrendingMoviesThisWeek()= appDataSource.getTrendingMoviesThisWeek().results
    override suspend fun getTrendingMoviesToday()= appDataSource.getTrendingMoviesToday().results
}