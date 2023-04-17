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
import com.example.bookmyshowdemo.databinding.CreditItemLayoutBinding
import com.example.bookmyshowdemo.databinding.MovieItemLayoutBinding

class CreditsAdapter(private val context: Context, private val creditList: List<Credit>, private val isCast:Boolean) :
    RecyclerView.Adapter<CreditsAdapter.MovieViewHolder>() {
    private lateinit var binding: CreditItemLayoutBinding
    // Define the ViewHolder class
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Override onCreateViewHolder to inflate the item layout and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        binding = CreditItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding.root)
    }

    // Override onBindViewHolder to set the data for each item in the list
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = creditList[position]
        binding.tvName.text = movie.name
        if(isCast){
            binding.tvCharacter.text = movie.character
        }else{
            binding.tvCharacter.text = movie.job
        }
        binding.imgPoster.loadImageFromUrl(Constant.BASE_URL_IMAGE+movie.profile_path)
    }

    // Override getItemCount to return the number of items in the list
    override fun getItemCount(): Int {
        return creditList.size
    }
}
