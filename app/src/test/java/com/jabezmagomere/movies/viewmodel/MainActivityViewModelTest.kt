package com.jabezmagomere.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jabezmagomere.movies.LiveDataTestUtil
import com.jabezmagomere.movies.TestUtil
import com.jabezmagomere.movies.MainCoroutineRule
import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.data.db.MoviesDao
import com.jabezmagomere.movies.data.repository.FakeMovieRepository
import com.jabezmagomere.movies.data.repository.MovieRepository
import com.jabezmagomere.movies.ui.viewmodel.MainActivityViewModel
import com.jabezmagomere.movies.util.Constants
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.Matchers.*
import org.hamcrest.MatcherAssert.*

@ExperimentalCoroutinesApi
class MainActivityViewModelTest{

    lateinit var viewModel:MainActivityViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val trendingTodayMovies = TestUtil.provideListofTestMovies().filter {
        it.category == Constants.TRENDING_TODAY
    }


    @MockK
    lateinit var moviesDao: MoviesDao
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp(){
        println("It started")
        MockKAnnotations.init(this)
        movieRepository = FakeMovieRepository(moviesDao)
        viewModel = MainActivityViewModel(movieRepository)
    }

    @Test
    fun `test trending movies today`(){
        val trendingMoviesLiveData = MutableLiveData<List<Movie>>()
        trendingMoviesLiveData.value= trendingTodayMovies
        coEvery {
           moviesDao.getAllMoviesPerCategory(Constants.TRENDING_TODAY)
        } returns trendingMoviesLiveData

        mainCoroutineRule.runBlockingTest{
            println("Test")
            LiveDataTestUtil.getValue(viewModel.trendingMoviesToday).forEach { movie->
                assertThat(movie.category, `is`(Constants.TRENDING_TODAY))
            }
        }
        coVerify {
                movieRepository.getTrendingMoviesToday()
        }
    }


    @After
    fun tearDown(){
        unmockkAll()
    }
}