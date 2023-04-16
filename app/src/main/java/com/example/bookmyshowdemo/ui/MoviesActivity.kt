package com.example.bookmyshowdemo.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookmyshowdemo.data.ApiResponse
import com.example.bookmyshowdemo.data.MovieRepository
import com.example.bookmyshowdemo.data.model.Movie
import com.example.bookmyshowdemo.databinding.ActivityMoviesBinding
import com.example.bookmyshowdemo.viewmodel.MovieViewModel
import com.example.bookmyshowdemo.viewmodel.MovieViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

class MoviesActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var binding: ActivityMoviesBinding
    private lateinit var mContext: Context
    private lateinit var movieList: ArrayList<Movie>
    private lateinit var filterMovieList: ArrayList<Movie>
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext=this
        initView()
        callMovieListApi()
        observeViewModel()
    }

    private fun callMovieListApi() {
        viewModel.getNowPlayingMovies()
    }

    private fun initView() {
        val repository = MovieRepository()
        filterMovieList = ArrayList()
        movieList = ArrayList()
        viewModel = ViewModelProvider(this, MovieViewModelFactory(repository))[MovieViewModel::class.java]
        binding.movieRecyclerView.apply { layoutManager= LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) }

        binding.searchBar.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*val searchText = s.toString().lowercase().trim()
                if (searchText.isNotEmpty()) {
                    filterMovieList.clear()
                    for (movie in movieList) {
                        val words = movie.original_title.trim().lowercase().split(" ")
                        for (word in words) {
                            if (word.isNotEmpty() && word.startsWith(searchText)) {
                                filterMovieList.add(movie)
                                break
                            }
                        }
                    }
                    setAdapter(filterMovieList)
                } else {
                    setAdapter(movieList)
                }*/

                val searchText = s.toString().lowercase().trim()
                if (searchText.isNotEmpty()) {
                    filterMovieList.clear()
                    for (movie in movieList) {
                        val words = movie.original_title.trim().lowercase().split(" ")
                        val searchWords = searchText.trim().split(" ")
                        var isMatch = true
                        for (searchWord in searchWords) {
                            var hasMatched = false
                            for (word in words) {
                                if (word.isNotEmpty() && word.startsWith(searchWord)) {
                                    hasMatched = true
                                    break
                                }
                            }
                            if (!hasMatched) {
                                isMatch = false
                                break
                            }
                        }
                        if (isMatch) {
                            filterMovieList.add(movie)
                        }
                    }
                    setAdapter(filterMovieList)
                } else {
                    setAdapter(movieList)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun setAdapter(movieList: ArrayList<Movie>) {
        movieAdapter = MovieListAdapter(mContext, movieList, this)
        binding.movieRecyclerView.adapter=movieAdapter
    }

    private fun observeViewModel() {
        viewModel.moviesLiveData.observe(this) {
            when (it) {
                is ApiResponse.Success -> {
                    val movieResponse = it.data
                    // Do something with the movies list
                    movieList = movieResponse as ArrayList<Movie>
                    setAdapter(movieList)
                }
                is ApiResponse.Error -> {
                    val message = it.message
                    // Handle the error message
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.isLoading.observe(this) {
            if (it){
                binding.progress.visibility = View.VISIBLE
            }else{
                binding.progress.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(itemId: String) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", itemId)
        startActivity(intent)
    }
}