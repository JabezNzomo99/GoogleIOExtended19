package com.jabezmagomere.movies.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movies")
    fun getAllMovies():LiveData<List<Movie>>

    @Query("SELECT * FROM Movies WHERE category=:category")
    fun getAllMoviesPerCategory(category:String):LiveData<List<Movie>>

    @Query("SELECT * FROM Movies WHERE id=:id")
    fun getMovie(id:Int):LiveData<Movie>

    @Query("DELETE FROM Movies")
    suspend fun clearMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<Movie>)

}