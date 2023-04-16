package com.example.bookmyshowdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmyshowdemo.data.ApiResponse
import com.example.bookmyshowdemo.data.MovieRepository
import com.example.bookmyshowdemo.data.model.CreditResponse
import com.example.bookmyshowdemo.data.model.Movie
import com.example.bookmyshowdemo.data.model.MovieResponse
import com.example.bookmyshowdemo.data.model.ReviewResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<ApiResponse<Movie>>()
    val moviesLiveData: LiveData<ApiResponse<Movie>> get() = _moviesLiveData

    private val _creditsLiveData = MutableLiveData<ApiResponse<CreditResponse>>()
    val creditsLiveData: LiveData<ApiResponse<CreditResponse>> get() = _creditsLiveData

    private val _similarLiveData = MutableLiveData<ApiResponse<MovieResponse>>()
    val similarLiveData: LiveData<ApiResponse<MovieResponse>> get() = _similarLiveData

    private val _reviewLiveData = MutableLiveData<ApiResponse<ReviewResponse>>()
    val reviewLiveData: LiveData<ApiResponse<ReviewResponse>> get() = _reviewLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getMovieDetails(movieId:String) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    //_moviesLiveData.value = repository.getMovieDetails(movieId)

                    val movieDetails = viewModelScope.async { repository.getMovieDetails(movieId) }
                    val credits = viewModelScope.async { repository.getCredits(movieId) }
                    val similar = viewModelScope.async { repository.getSimilar(movieId) }
                    val review = viewModelScope.async { repository.getReviews(movieId) }
                    _moviesLiveData.value = movieDetails.await()
                    _creditsLiveData.value = credits.await()
                    _similarLiveData.value = similar.await()
                    _reviewLiveData.value = review.await()
                }catch (e: Exception) {
                    ApiResponse.Error(e.message ?: "Unknown error")
                } finally {
                    _isLoading.value = false
                }
            }
        }
}

