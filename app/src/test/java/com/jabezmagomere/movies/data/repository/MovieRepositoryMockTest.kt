package com.jabezmagomere.movies.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jabezmagomere.movies.LiveDataTestUtil
import com.jabezmagomere.movies.TestUtil
import com.jabezmagomere.movies.data.dao.observeOnce
import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.data.db.MoviesDao
import com.jabezmagomere.movies.util.Constants
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.Matchers.*
import org.hamcrest.MatcherAssert.*

@ExperimentalCoroutinesApi
class MovieRepositoryMockTest{
    lateinit var movieRepository: MovieRepository

    //Mock the movies dao instead of using the real implementation
    @MockK
    lateinit var moviesDao: MoviesDao

    //List containing dummy `trending today` movies to be used for testing
    private val trendingTodayMovies= TestUtil.provideListofTestMovies().filter {
        it.category == Constants.TRENDING_TODAY
    }

    //Rule ensures that all asynchronous tasks are executed synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //Run before the tests
    @Before
    fun setUp(){
        //Setup mocks
        MockKAnnotations.init(this)
        movieRepository = FakeMovieRepository(moviesDao)
    }

    @Test
    fun `test getTrendingMoviesToday()`(){
        val trendingMoviesLiveData = MutableLiveData<List<Movie>>()
        trendingMoviesLiveData.value= trendingTodayMovies
        every {
            moviesDao.getAllMoviesPerCategory(Constants.TRENDING_TODAY)
        }returns MutableLiveData()
        runBlockingTest {
            val retrievedMovies = LiveDataTestUtil.getValue(movieRepository.getTrendingMoviesToday())
            assertThat(retrievedMovies.isNullOrEmpty(), `is`(false))
            retrievedMovies.forEach { movie->
                assertThat(movie.category, `is`(Constants.TRENDING_TODAY))
            }
        }
        verify {
            moviesDao.getAllMoviesPerCategory(Constants.TRENDING_TODAY)
        }
    }
    @After
    fun tearDown(){
        unmockkAll()
    }
}