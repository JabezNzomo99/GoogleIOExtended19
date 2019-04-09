package com.jabezmagomere.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.jabezmagomere.movies.data.models.Movie
import com.jabezmagomere.movies.ui.ScopedActivity
import com.jabezmagomere.movies.ui.adapters.CategoryRecyclerViewAdapter
import com.jabezmagomere.movies.ui.view.Category
import com.jabezmagomere.movies.ui.viewmodel.MainAcitvityViewModel
import com.jabezmagomere.movies.ui.viewmodel.MainActivityViewModelFactory
import com.jabezmagomere.movies.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import retrofit2.HttpException
import java.lang.StringBuilder
import java.net.UnknownHostException

class MainActivity : ScopedActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val mainActivityViewModelFactory by instance<MainActivityViewModelFactory>()
    private lateinit var mainActivityViewModel: MainAcitvityViewModel
    private val categories = ArrayList<Category>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProviders.of(this, mainActivityViewModelFactory)
                .get(MainAcitvityViewModel::class.java)
        handleExceptions()
        swipeRefreshLayout.isRefreshing =true
        launch {
            getTrendingMoviesThisWeek()
            getTrendingMoviesToday()
            displayData()
            @UiThread
            swipeRefreshLayout.isRefreshing =false
        }
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener {
            launch {
                getTrendingMoviesThisWeek()
                getTrendingMoviesToday()
                displayData()
                @UiThread
                swipeRefreshLayout.isRefreshing = false
            }

        }


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
        mainActivityViewModel.getTrendingMoviesThisWeek()
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
        mainActivityViewModel.getTrendingMoviesToday()
    }

    private fun setupRecyclerView(categories:ArrayList<Category>){
        if(categories.size>0){
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity,
                    RecyclerView.VERTICAL, false)
                adapter = CategoryRecyclerViewAdapter(categories, this@MainActivity)

            }
        }
    }

    private fun handleExceptions(){
        mainActivityViewModel.exception.observe(this, Observer { exception->
            if(exception!=null) {
                displayErrorLayout()
               displayNetworkError()
            }
        })

    }
    private fun displayNetworkError(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.network_error_layout,null)
        val dialog= AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .show()
        dialogView.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun displayErrorLayout(){
        errorLayout.visibility = View.VISIBLE
        constraintLayout.visibility = View.GONE

    }

}