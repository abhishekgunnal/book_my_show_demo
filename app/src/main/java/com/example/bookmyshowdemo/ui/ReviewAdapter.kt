package com.example.bookmyshowdemo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyshowdemo.R
import com.example.bookmyshowdemo.data.Constant
import com.example.bookmyshowdemo.data.model.Review

class ReviewAdapter(private val context: Context, private val reviewList: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.MovieViewHolder>() {

    // Define the ViewHolder class
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define the views in the item layout
        val name: TextView = itemView.findViewById(R.id.tvName)
        val rating: TextView = itemView.findViewById(R.id.tvRating)
        val content: TextView = itemView.findViewById(R.id.tvContent)

    }

    // Override onCreateViewHolder to inflate the item layout and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.review_item_layout, parent, false)
        return MovieViewHolder(itemView)
    }

    // Override onBindViewHolder to set the data for each item in the list
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val review = reviewList[position]
        holder.name.text = review.author_details.username
        holder.rating.text = review.author_details.rating+"/10"
        holder.name.text = review.author_details.username

    }

    // Override getItemCount to return the number of items in the list
    override fun getItemCount(): Int {
        return reviewList.size
    }
}
