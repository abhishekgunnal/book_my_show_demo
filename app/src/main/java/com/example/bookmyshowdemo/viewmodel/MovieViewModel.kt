package com.example.bookmyshowdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmyshowdemo.data.ApiResponse
import com.example.bookmyshowdemo.data.MovieRepository
import com.example.bookmyshowdemo.data.model.Movie
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    val moviesLiveData: LiveData<ApiResponse<List<Movie>>> get() = _moviesLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



        fun getNowPlayingMovies() {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    _moviesLiveData.value = repository.getNowPlayingMovies()
                }catch (e: Exception) {
                    ApiResponse.Error(e.message ?: "Unknown error")
                } finally {
                    _isLoading.value = false
                }
            }
        }
}

