package com.jabezmagomere.movies.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    val adult: Boolean, // false
    @SerializedName("backdrop_path")
    val backdropPath: String, // /s9I2LmQMYCanl6DvC3X1AOHs2r8.jpg
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int, // 399361
    @SerializedName("original_language")
    val originalLanguage: String, // en
    @SerializedName("original_title")
    val originalTitle: String, // Triple Frontier
    val overview: String, // Struggling to make ends meet, former special ops soldiers reunite for a high-stakes heist: stealing $75 million from a South American drug lord.
    @SerializedName("poster_path")
    val posterPath: String, // /aBw8zYuAljVM1FeK5bZKITPH8ZD.jpg
    @SerializedName("release_date")
    val releaseDate: String, // 2019-03-06
    val title: String, // Triple Frontier
    val video: Boolean, // false
    @SerializedName("vote_average")
    val voteAverage: Double, // 6.2
    @SerializedName("vote_count")
    val voteCount: Int, // 761
    val popularity: Double // 72.034
)