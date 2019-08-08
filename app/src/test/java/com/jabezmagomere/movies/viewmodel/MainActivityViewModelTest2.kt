package com.jabezmagomere.movies.viewmodel

import com.jabezmagomere.movies.data.repository.MovieRepository
import com.jabezmagomere.movies.data.repository.observeOnce

import androidx.lifecycle.MutableLiveData
import com.jabezmagomere.movies.CustomInstantTaskExecutorRule
import com.jabezmagomere.movies.TestUtil
import com.jabezmagomere.movies.ui.viewmodel.MainActivityViewModel
import com.jabezmagomere.movies.util.Constants
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.Matchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

/**
 * Uses JUnit5
 */
@ExperimentalCoroutinesApi
//Use @Extensions to load rules as opposed to @Rule annotation in jUnit4
@Extensions(
    ExtendWith(CustomInstantTaskExecutorRule::class)
)
//Test instance is created once per the entire test session
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainViewModelTest2 {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private val mockMovieRepository: MovieRepository = mockk()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @BeforeAll
    fun setUp(){
        MockKAnnotations.init()
        Dispatchers.setMain(mainThreadSurrogate)
        mainActivityViewModel = MainActivityViewModel(movieRepository = mockMovieRepository)
    }

    @Test
    fun `test whether discoverActionMovies() is invoked when actionMovies in viewModel is triggered`(){
        coEvery{
            mockMovieRepository.discoverActionMovies()
        }returns MutableLiveData(TestUtil.provideListOfCategoryMovies(Constants.ACTION))
        runBlocking {
            launch (Dispatchers.Main){
                mainActivityViewModel.actionMovies.observeOnce {  }
            }
        }
        coVerify {
            mockMovieRepository.discoverActionMovies()
        }
    }

    @Test
    fun `test whether discoverComedyMovies() is invoked when actionMovies in viewModel is triggered`(){
        coEvery{
            mockMovieRepository.discoverActionMovies()
        }returns MutableLiveData(TestUtil.provideListOfCategoryMovies(Constants.ACTION))
        runBlocking(Dispatchers.Main){
            mainActivityViewModel.actionMovies.observeOnce { movies->
                movies.forEach { movie->
                    assertThat(movie.category, `is`(Constants.ACTION))

                }

            }
        }
        coVerify {
            mockMovieRepository.discoverActionMovies()
        }
    }

    @AfterAll
    fun tearDown(){
        unmockkAll()
        Dispatchers.resetMain()
    }
}