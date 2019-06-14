package com.jabezmagomere.movies.util

import com.jabezmagomere.movies.data.db.Movie

interface CustomOnClickListener {
    fun onItemClick(movie: Movie)
}