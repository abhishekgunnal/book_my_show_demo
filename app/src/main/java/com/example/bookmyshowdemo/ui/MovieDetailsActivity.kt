package com.example.bookmyshowdemo.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.bookmyshowdemo.R
import com.example.bookmyshowdemo.data.ApiResponse
import com.example.bookmyshowdemo.data.Constant
import com.example.bookmyshowdemo.data.MovieRepository
import com.example.bookmyshowdemo.databinding.ActivityMovieDetailsBinding
import com.example.bookmyshowdemo.viewmodel.MovieDetailsViewModel
import com.example.bookmyshowdemo.viewmodel.MovieViewModelFactory

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var mContext: Context
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var similarAdapter: SimilarAdapter
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext=this
        initView()
        observeViewModel()
    }
    private fun initView() {
        val repository = MovieRepository()
        viewModel = ViewModelProvider(this, MovieViewModelFactory(repository))[MovieDetailsViewModel::class.java]
        val movieId = intent.getStringExtra("movieId")
        if (movieId != null) {
            callMovieDetailsApi(movieId)
        }
        binding.castRecyclerView.apply { layoutManager= LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)}
        binding.crewRecyclerView.apply { layoutManager= LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)}
        binding.similarRecyclerView.apply { layoutManager= LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)}
        binding.reviewRecyclerView.apply { layoutManager= LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)}
    }

    private fun callMovieDetailsApi(movieId: String) {
        viewModel.getMovieDetails(movieId)
    }

    private fun observeViewModel() {
        viewModel.moviesLiveData.observe(this) {
            when (it) {
                is ApiResponse.Success -> {
                    val movieDetails = it.data
                    // Do something with the movies list
                    binding.ivMovie.loadImageFromUrl(Constant.BASE_URL_IMAGE_ORIGINAL+movieDetails.backdrop_path)
                    binding.tvTitle.text = movieDetails.original_title
                    binding.tvDesp.text = movieDetails.overview
                }
                is ApiResponse.Error -> {
                    val message = it.message
                    // Handle the error message
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.creditsLiveData.observe(this) {
            when (it) {
                is ApiResponse.Success -> {
                    val credit = it.data
                    // Do something with the movies list
                    creditsAdapter = CreditsAdapter(mContext, credit.cast, true)
                    binding.castRecyclerView.adapter=creditsAdapter

                    creditsAdapter = CreditsAdapter(mContext, credit.crew, false)
                    binding.crewRecyclerView.adapter=creditsAdapter
                }
                is ApiResponse.Error -> {
                    val message = it.message
                    // Handle the error message
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.similarLiveData.observe(this) {
            when (it) {
                is ApiResponse.Success -> {
                    val similar = it.data
                    // Do something with the movies list
                    similarAdapter = SimilarAdapter(mContext, similar.results)
                    binding.similarRecyclerView.adapter=similarAdapter

                }
                is ApiResponse.Error -> {
                    val message = it.message
                    // Handle the error message
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.reviewLiveData.observe(this) {
            when (it) {
                is ApiResponse.Success -> {
                    val review = it.data
                    // Do something with the movies list
                    reviewAdapter = ReviewAdapter(mContext, review.results)
                    binding.reviewRecyclerView.adapter=reviewAdapter

                }
                is ApiResponse.Error -> {
                    val message = it.message
                    // Handle the error message
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
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
}
fun ImageView.loadImageFromUrl(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.default_cover)
        .error(R.drawable.default_cover)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerInside()
        .priority(Priority.HIGH)
        .into(this)
}