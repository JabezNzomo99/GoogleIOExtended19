package com.jabezmagomere.movies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jabezmagomere.movies.R
import com.jabezmagomere.movies.data.db.Movie
import com.jabezmagomere.movies.util.Constants
import com.jabezmagomere.movies.util.CustomOnClickListener
import kotlinx.android.synthetic.main.row_movie_item.view.*

class MovieRecyclerViewAdapter(private val movies:List<Movie>, private val listener: CustomOnClickListener):RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>(){
    class MovieViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.imageViewMovie
        val textViewTitle = itemView.textViewTitle

        fun bind(movie: Movie, listener:CustomOnClickListener){
            Glide.with(itemView.context).load("${Constants.IMAGE_URL}${movie.posterPath}").into(imageView);
            textViewTitle.text = movie.title
            itemView.setOnClickListener {
                listener.onItemClick(movie)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecyclerViewAdapter.MovieViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.row_movie_item,parent,false)
        return MovieViewHolder(v)

    }

    override fun onBindViewHolder(holder: MovieRecyclerViewAdapter.MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie,listener)
    }

    override fun getItemCount(): Int = movies.size

}