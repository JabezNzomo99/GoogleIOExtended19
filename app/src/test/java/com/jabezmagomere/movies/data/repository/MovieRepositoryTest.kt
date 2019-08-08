package com.jabezmagomere.movies.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.jabezmagomere.movies.TestUtil
import com.jabezmagomere.movies.OneTimeObserver
import com.jabezmagomere.movies.data.dao.observeOnce
import com.jabezmagomere.movies.data.db.MovieDatabase
import com.jabezmagomere.movies.data.db.MoviesDao
import com.jabezmagomere.movies.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import org.hamcrest.Matchers.*
import org.hamcrest.MatcherAssert.*

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MovieRepositoryTest{

    lateinit var movieRepository:MovieRepository
    lateinit var moviesDao: MoviesDao
    lateinit var movieDatabase: MovieDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        movieDatabase = TestUtil.provideRoomTestDatabase()
        moviesDao = movieDatabase.moviesDao()
        movieRepository = FakeMovieRepository(moviesDao)
        //insert data for testing to the database
        runBlockingTest {
            moviesDao.insertMovies(TestUtil.provideListofTestMovies())
        }
    }

    @Test
    fun `test whether getTrendingMoviesToday() returns trending movies for today`()= runBlockingTest {
        movieRepository.getTrendingMoviesToday().observeOnce { movies->
            movies.forEach { movie->
                assertThat(movie.category, `is`(Constants.TRENDING_TODAY))
            }
        }
    }

    @Test
    fun `test whether getTrendingMoviesThisWeek() returns trending movies for this week`()= runBlockingTest {
        movieRepository.getTrendingMoviesThisWeek().observeOnce { movies->
            movies.forEach { movie->
                assertThat(movie.category, `is`(Constants.TRENDING_THIS_WEEK))
            }
        }
    }

    @Test
    fun `test whether discoverActionMovies() returns trending action movies`()= runBlockingTest {
        movieRepository.discoverActionMovies().observeOnce { movies->
            movies.forEach { movie->
                assertThat(movie.category, `is`(Constants.ACTION))
            }
        }
    }

    @Test
    fun `test whether discoverComedyMovies() returns trending comedy movies`()= runBlockingTest {
        movieRepository.discoverComedyMovies().observeOnce { movies->
            movies.forEach { movie->
                assertThat(movie.category, `is`(Constants.COMEDY))
            }
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