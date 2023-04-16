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

class MovieListAdapter(private val context: Context, private var movieList: ArrayList<Movie>,private val onItemClickListener:OnItemClickListener) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    // Define the ViewHolder class
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define the views in the item layout
        val movieTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val movieReleaseDate: TextView = itemView.findViewById(R.id.tvReleaseDate)
        val movieImage: ImageView = itemView.findViewById(R.id.imgPoster)
        val movieCard: CardView = itemView.findViewById(R.id.cardView)
    }

    // Override onCreateViewHolder to inflate the item layout and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
        return MovieViewHolder(itemView)
    }

    // Override onBindViewHolder to set the data for each item in the list
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.movieTitle.text = movie.original_title
        holder.movieReleaseDate.text = movie.release_date
        holder.movieImage.loadImageFromUrl(Constant.BASE_URL_IMAGE+movie.poster_path)


        holder.movieCard.setOnClickListener {
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
