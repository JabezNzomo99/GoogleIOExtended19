package com.jabezmagomere.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.jabezmagomere.movies.MainActivity
import com.jabezmagomere.movies.R
import com.jabezmagomere.movies.util.Constants
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val bundle = intent.getBundleExtra(MainActivity.MOVIE_BUNDLE)
        setUpViews(bundle)
    }

    private fun setUpViews(bundle: Bundle){
        Glide.with(this).load("${Constants.IMAGE_URL}${bundle.getString(MainActivity.MOVIE_BACKDROP)}").into(imageViewBackdrop)
        Glide.with(this).load("${Constants.IMAGE_URL}${bundle.getString(MainActivity.MOVIE_POSTER)}").into(imageViewMovie)
        textViewTitle.text = bundle.getString(MainActivity.MOVIE_TITLE)
        textViewOverview.text = bundle.getString(MainActivity.MOVIE_OVERVIEW)
    }
}