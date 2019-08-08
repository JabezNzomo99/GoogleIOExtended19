package com.jabezmagomere.movies.data.repository

import androidx.lifecycle.LiveData
import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.data.db.MoviesDao
import com.jabezmagomere.movies.util.Constants

class FakeMovieRepository(private val moviesDao: MoviesDao) : MovieRepository{
    override suspend fun getTrendingMoviesThisWeek(): LiveData<List<Movie>> = moviesDao.getAllMoviesPerCategory(Constants.TRENDING_THIS_WEEK)

    override suspend fun getTrendingMoviesToday(): LiveData<List<Movie>> = moviesDao.getAllMoviesPerCategory(Constants.TRENDING_TODAY)

    override suspend fun discoverActionMovies(): LiveData<List<Movie>> = moviesDao.getAllMoviesPerCategory(Constants.ACTION)

    override suspend fun discoverComedyMovies(): LiveData<List<Movie>> = moviesDao.getAllMoviesPerCategory(Constants.COMEDY)

}