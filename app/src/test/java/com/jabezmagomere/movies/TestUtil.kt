package com.jabezmagomere.movies

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.data.db.MovieDatabase
import com.jabezmagomere.movies.util.Constants
import retrofit2.Response

object TestUtil {

    fun provideRoomTestDatabase() = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),MovieDatabase::class.java)
        .allowMainThreadQueries()
        .build()
    /**
     * Function used to create a dummy movie used for testing
     * @param testId: unique movie id
     * @param movieTitle: title of the movie
     * @param movieCategory: Category the movie belongs to, e.g. Action
     * @return Movie: returns a dummy movie used for testing
     */
    fun provideTestMovie(testId:Int, movieTitle:String="IronMan", movieCategory: String =Constants.TRENDING_TODAY)=Movie().apply {
            id = testId
            title = movieTitle
            category = movieCategory
        }


    fun provideListofTestMovies()=listOf(
            provideTestMovie(1,"Rambo",Constants.ACTION),
            provideTestMovie(2, "Jumanji",Constants.COMEDY),
            provideTestMovie(3,"Avengers", Constants.ACTION),
            provideTestMovie(4,"Lion King", Constants.TRENDING_TODAY),
            provideTestMovie(5,"Hobbs and Shaw", Constants.TRENDING_THIS_WEEK),
            provideTestMovie(6,"Mic and Maestro", Constants.TRENDING_TODAY)
        )

    fun provideListOfCategoryMovies(category:String)= provideListofTestMovies().filter { movie->
        movie.category == category

    }
}

