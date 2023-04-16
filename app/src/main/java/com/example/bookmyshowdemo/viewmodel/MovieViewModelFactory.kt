package com.example.bookmyshowdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookmyshowdemo.data.MovieRepository

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MovieViewModel::class.java) ->
                    MovieViewModel(repository) as T
                isAssignableFrom(MovieDetailsViewModel::class.java) ->
                    MovieDetailsViewModel(repository) as T
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}
