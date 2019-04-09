package com.jabezmagomere.movies.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jabezmagomere.movies.R
import com.jabezmagomere.movies.data.models.Movie
import com.jabezmagomere.movies.util.Constants
import kotlinx.android.synthetic.main.row_movie_item.view.*

class MovieRecyclerViewAdapter(private val movies:List<Movie>, private val context:Context):RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>(){
    class MovieViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.imageViewMovie
        val textViewTitle = itemView.textViewTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecyclerViewAdapter.MovieViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.row_movie_item,parent,false)
        return MovieViewHolder(v)

    }

    override fun onBindViewHolder(holder: MovieRecyclerViewAdapter.MovieViewHolder, position: Int) {
        val movie = movies[position]
        Glide.with(context).load("${Constants.IMAGE_URL}${movie.posterPath}").into(holder.imageView);
        holder.imageView
        holder.textViewTitle.text = movie.title
    }

    override fun getItemCount(): Int = movies.size

}