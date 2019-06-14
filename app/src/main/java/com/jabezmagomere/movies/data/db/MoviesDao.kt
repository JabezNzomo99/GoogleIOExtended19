package com.jabezmagomere.movies.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movies WHERE category=:category")
    fun getAllMovies(category:String):LiveData<List<Movie>>

    @Query("DELETE FROM Movies")
    suspend fun clearMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert
    suspend fun insertMovies(movie: List<Movie>)

}