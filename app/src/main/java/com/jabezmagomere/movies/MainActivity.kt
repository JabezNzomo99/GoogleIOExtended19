package com.jabezmagomere.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.snackbar.Snackbar
import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.ui.MovieDetailActivity
import com.jabezmagomere.movies.ui.adapters.CategoryRecyclerViewAdapter
import com.jabezmagomere.movies.ui.view.Category
import com.jabezmagomere.movies.ui.viewmodel.MainAcitvityViewModel
import com.jabezmagomere.movies.ui.viewmodel.MainActivityViewModelFactory
import com.jabezmagomere.movies.util.Constants
import com.jabezmagomere.movies.util.CustomOnClickListener
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val mainActivityViewModelFactory by instance<MainActivityViewModelFactory>()
    private lateinit var mainActivityViewModel: MainAcitvityViewModel
    private val categories = ArrayList<Category>()
    companion object {
        const val MOVIE_TITLE = "movie_title"
        const val MOVIE_BUNDLE = "movie_data"
        const val MOVIE_POSTER = "movie_poster"
        const val MOVIE_OVERVIEW = "movie_overview"
        const val MOVIE_BACKDROP = "movie_backdrop"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(categories.size>0)categories.clear()
        mainActivityViewModel = ViewModelProviders.of(this, mainActivityViewModelFactory)
                .get(MainAcitvityViewModel::class.java)
//        setProgress()
        getTrendingMoviesToday()
        getTrendingMoviesThisWeek()
        discoverActionMovies()
        discoverComedyMovies()
        displayData()
    }

    private fun displayData(){
        mainActivityViewModel.allCategory.observe(this, Observer {categories->
            setupRecyclerView(categories)
        })

    }
    private fun getTrendingMoviesThisWeek() {
        mainActivityViewModel.trendingMoviesThisWeek.observe(this, Observer { movies->
            if(!movies.isNullOrEmpty()){
                displayTrendingMoviesThisWeek(movies)
                categories.add(Category("Trending this week",movies))
                mainActivityViewModel.allCategory.postValue(categories)
            }
        })
    }

    private fun displayTrendingMoviesThisWeek(movies: List<Movie>) {
        val imageList = ArrayList<SlideModel>()
        movies.forEach {movie->
            imageList.add(SlideModel("${Constants.IMAGE_URL}${movie.backdropPath}",movie.title))
        }
        imageSlider.setImageList(imageList)
        imageSlider.setItemClickListener(object :ItemClickListener{
            override fun onItemSelected(position: Int) {
                val movieItem = imageList[position]
                Toast.makeText(this@MainActivity, movieItem.title, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getTrendingMoviesToday(){
        mainActivityViewModel.trendingMoviesToday.observe(this, Observer {movies->
            if(!movies.isNullOrEmpty()){
                categories.add(Category("Trending Movies Today",movies))
                mainActivityViewModel.allCategory.postValue(categories)
            }
        })
    }

    private fun discoverActionMovies(){
        mainActivityViewModel.actionMovies.observe(this, Observer {movies->
            if(!movies.isNullOrEmpty()){
                categories.add(Category("Action Movies",movies))
                mainActivityViewModel.allCategory.postValue(categories)
            }
        })
    }

    private fun discoverComedyMovies(){
        mainActivityViewModel.comedyMovies.observe(this, Observer {movies->
            if(!movies.isNullOrEmpty()){
                categories.add(Category("Comedy Movies",movies))
                mainActivityViewModel.allCategory.postValue(categories)
            }
        })
    }


    private fun setupRecyclerView(categories:ArrayList<Category>){
        if(categories.size>0){
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity,
                    RecyclerView.VERTICAL, false)
                adapter = CategoryRecyclerViewAdapter(categories, object : CustomOnClickListener{
                    override fun onItemClick(movie: Movie) {
                        val bundle = Bundle()
                        bundle.putString(MOVIE_TITLE,movie.title)
                        bundle.putString(MOVIE_BACKDROP, movie.backdropPath)
                        bundle.putString(MOVIE_OVERVIEW,movie.overview)
                        bundle.putString(MOVIE_POSTER,movie.posterPath)
                        val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
                        intent.putExtra(MOVIE_BUNDLE,bundle)
                        startActivity(intent)
                    }

                })
            }
        }
    }

//    private fun setProgress(){
//        mainActivityViewModel.isLoading.observe(this, Observer { state->
//            if(state){
//                constraintLayout.visibility = View.INVISIBLE
//                progress_layout.visibility = View.VISIBLE
//            }else{
//                constraintLayout.visibility = View.VISIBLE
//                progress_layout.visibility = View.INVISIBLE
//            }
//        })
//    }

}