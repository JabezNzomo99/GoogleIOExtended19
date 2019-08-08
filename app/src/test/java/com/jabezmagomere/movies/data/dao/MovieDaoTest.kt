package com.jabezmagomere.movies.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.jabezmagomere.movies.TestUtil
import com.jabezmagomere.movies.OneTimeObserver
import com.jabezmagomere.movies.data.db.MovieDatabase
import com.jabezmagomere.movies.data.db.MoviesDao
import com.jabezmagomere.movies.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class MovieDaoTest{

    lateinit var movieDatabase: MovieDatabase
    lateinit var moviesDao: MoviesDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        movieDatabase = TestUtil.provideRoomTestDatabase()
        moviesDao = movieDatabase.moviesDao()
    }


    @Test
    fun `test whether a movie is inserted to the database `() = runBlockingTest {
            val testMovie = TestUtil.provideTestMovie(1)
            moviesDao.insertMovie(testMovie)
            //Hamcrest assertions are more readable than Junit4
            moviesDao.getMovie(1).observeOnce { movie ->
                assertThat(movie, `is`(equalTo(testMovie)))
            }
    }

    @Test
    fun `test whether getAllMovies() retrieves the movies of a specific category`()= runBlockingTest {
        //*Given* movies in the table
        moviesDao.insertMovies(TestUtil.provideListofTestMovies())
        //*when* getAllMoviesPerCategory is called and the specific category name is passed
        moviesDao.getAllMoviesPerCategory(Constants.ACTION).observeOnce { movies ->
            //assert *that* all the movies retrieved belong to ACTION category
            movies.forEach {
                assertThat(it.category, `is`(Constants.ACTION))
            }
        }
    }

    @Test
    fun `test whether clearing the database removes all movies`()=runBlockingTest {
        //*Given* movies in the table
        moviesDao.insertMovies(TestUtil.provideListofTestMovies())
        //*when* clear() is called
        moviesDao.clearMovies()
        //assert *that all movies are cleared*
        moviesDao.getAllMovies().observeOnce { movies ->
            //Hamcrest assertions
            assertThat(movies.size, `is`(0))
            //Junit assertions
            assertEquals(true, movies.isNullOrEmpty())
        }
    }

    @After
    @Throws(IOException::class)
    fun tearDown(){
        movieDatabase.close()

    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}
