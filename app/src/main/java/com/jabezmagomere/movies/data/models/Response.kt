package com.jabezmagomere.movies.data.models

import com.google.gson.annotations.SerializedName
import com.jabezmagomere.movies.data.db.Movie

data class Response(
    val page: Int, // 1
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int, // 1000
    @SerializedName("total_results")
    val totalResults: Int // 20000
)