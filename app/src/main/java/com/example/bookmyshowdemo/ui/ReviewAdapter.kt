package com.example.bookmyshowdemo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyshowdemo.R
import com.example.bookmyshowdemo.data.Constant
import com.example.bookmyshowdemo.data.model.Review
import com.example.bookmyshowdemo.databinding.CreditItemLayoutBinding
import com.example.bookmyshowdemo.databinding.ReviewItemLayoutBinding

class ReviewAdapter(private val context: Context, private val reviewList: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.MovieViewHolder>() {
    private lateinit var binding: ReviewItemLayoutBinding
    // Define the ViewHolder class
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Override onCreateViewHolder to inflate the item layout and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        binding = ReviewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding.root)
    }

    // Override onBindViewHolder to set the data for each item in the list
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val review = reviewList[position]
        binding.tvName.text = review.author_details.username
        binding.tvRating.text = review.author_details.rating+"/10"
        binding.tvContent.text = review.content

    }

    // Override getItemCount to return the number of items in the list
    override fun getItemCount(): Int {
        return reviewList.size
    }
}
