package com.example.bookmyshowdemo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.bookmyshowdemo.R
import com.example.bookmyshowdemo.data.Constant
import com.example.bookmyshowdemo.data.model.Movie
import com.example.bookmyshowdemo.databinding.MovieItemLayoutBinding

class MovieListAdapter(private val context: Context, private var movieList: ArrayList<Movie>,private val onItemClickListener:OnItemClickListener) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private lateinit var binding: MovieItemLayoutBinding
    // Define the ViewHolder class
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Override onCreateViewHolder to inflate the item layout and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        binding = MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding.root)
    }

    // Override onBindViewHolder to set the data for each item in the list
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        binding.tvTitle.text = movie.original_title
        binding.tvReleaseDate.text = movie.release_date
        binding.imgPoster.loadImageFromUrl(Constant.BASE_URL_IMAGE+movie.poster_path)

        binding.cardView.setOnClickListener {
            onItemClickListener.onItemClick(movie.id)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun seFilteredList(movieList: ArrayList<Movie>){
        this.movieList.clear()
        this.movieList = movieList
        notifyDataSetChanged()
    }

    // Override getItemCount to return the number of items in the list
    override fun getItemCount(): Int {
        return movieList.size
    }
}
