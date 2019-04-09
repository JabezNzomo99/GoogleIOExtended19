package com.jabezmagomere.movies.data.network

import com.jabezmagomere.movies.data.models.Response

interface AppDataSource {
    suspend fun getTrendingMoviesThisWeek():Response
    suspend fun getTrendingMoviesToday(): Response
}