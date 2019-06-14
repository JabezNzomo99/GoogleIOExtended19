package com.jabezmagomere.movies.data.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Room allows you to create data classes that will be mapped to tables
 *
 */
@Entity(tableName = "Movies")
data class Movie(
    var adult: Boolean, // false
    @SerializedName("backdrop_path")
    var backdropPath: String?=null, // /s9I2LmQMYCanl6DvC3X1AOHs2r8.jpg
    @SerializedName("genre_ids")
    @Ignore
    var genreIds: List<Int>?=null,
    @PrimaryKey(autoGenerate = false)
    var id: Int, // 399361
    @SerializedName("original_language")
    var originalLanguage: String, // en
    @SerializedName("original_title")
    var originalTitle: String, // Triple Frontier
    var overview: String, // Struggling to make ends meet, former special ops soldiers reunite for a high-stakes heist: stealing $75 million from a South American drug lord.
    @SerializedName("poster_path")
    var posterPath: String, // /aBw8zYuAljVM1FeK5bZKITPH8ZD.jpg
    @SerializedName("release_date")
    var releaseDate: String, // 2019-03-06
    var title: String, // Triple Frontier
    var video: Boolean, // false
    @SerializedName("vote_average")
    var voteAverage: Double, // 6.2
    @SerializedName("vote_count")
    var voteCount: Int, // 761
    var popularity: Double, // 72.034,
    var category:String)
{
    constructor():this(false,"",null,0,"","","","","," +
            "","",false,0.0,0,0.0,"")
}