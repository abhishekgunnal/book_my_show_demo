package com.example.bookmyshowdemo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyshowdemo.R
import com.example.bookmyshowdemo.data.Constant
import com.example.bookmyshowdemo.data.model.Credit

class CreditsAdapter(private val context: Context, private val creditList: List<Credit>, private val isCast:Boolean) :
    RecyclerView.Adapter<CreditsAdapter.MovieViewHolder>() {

    // Define the ViewHolder class
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define the views in the item layout
        val name: TextView = itemView.findViewById(R.id.tvName)
        val character: TextView = itemView.findViewById(R.id.tvCharacter)
        val pic: ImageView = itemView.findViewById(R.id.imgPoster)
    }

    // Override onCreateViewHolder to inflate the item layout and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.credit_item_layout, parent, false)
        return MovieViewHolder(itemView)
    }

    // Override onBindViewHolder to set the data for each item in the list
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = creditList[position]
        holder.name.text = movie.name
        if(isCast){
            holder.character.text = movie.character
        }else{
            holder.character.text = movie.job
        }
        holder.pic.loadImageFromUrl(Constant.BASE_URL_IMAGE+movie.profile_path)
    }

    // Override getItemCount to return the number of items in the list
    override fun getItemCount(): Int {
        return creditList.size
    }
}
