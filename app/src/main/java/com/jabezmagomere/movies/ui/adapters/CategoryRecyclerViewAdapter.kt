package com.jabezmagomere.movies.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jabezmagomere.movies.R
import com.jabezmagomere.movies.ui.view.Category
import kotlinx.android.synthetic.main.row_category_item.view.*

class CategoryRecyclerViewAdapter(private val categories:List<Category>, private val context:Context):
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>(){
    inner class CategoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val recyclerView = itemView.recyclerViewMovies
        val textViewCategoryTitle = itemView.textViewCategory

    }
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecyclerViewAdapter.CategoryViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.row_category_item,parent,false)
        return CategoryViewHolder(v)    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryRecyclerViewAdapter.CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.textViewCategoryTitle.text = category.title
        val childLayoutManager = LinearLayoutManager(holder.recyclerView.context,LinearLayout.HORIZONTAL, false)
        childLayoutManager.initialPrefetchItemCount = 4
        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = MovieRecyclerViewAdapter(category.movies, context)
            setRecycledViewPool(viewPool)
        }

    }

}