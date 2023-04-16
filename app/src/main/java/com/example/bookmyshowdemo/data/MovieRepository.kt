package com.example.bookmyshowdemo.data

import com.example.bookmyshowdemo.data.model.CreditResponse
import com.example.bookmyshowdemo.data.model.Movie
import com.example.bookmyshowdemo.data.model.MovieResponse
import com.example.bookmyshowdemo.data.model.ReviewResponse

class MovieRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getNowPlayingMovies(): ApiResponse<List<Movie>> {
        try {
            val response = apiService.getNowPlayingMovies(Constant.apiKey)
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                return ApiResponse.Success(movies)
            }
            return ApiResponse.Error(response.message())
        } catch (e: Exception) {
            return ApiResponse.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getMovieDetails(movieId:String): ApiResponse<Movie> {
        try {
            val response = apiService.getMovieDetails(movieId, Constant.apiKey)
            if (response.isSuccessful) {
                val movieDetails: Movie = response.body() ?: return ApiResponse.Error("Movie details not found")
                return ApiResponse.Success(movieDetails)
            }
            return ApiResponse.Error(response.message())
        } catch (e: Exception) {
            return ApiResponse.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getCredits(movieId:String): ApiResponse<CreditResponse> {
        try {
            val response = apiService.getCredits(movieId, Constant.apiKey)
            if (response.isSuccessful) {
                val creditResponse: CreditResponse = response.body() ?: return ApiResponse.Error("Credits not found")
                return ApiResponse.Success(creditResponse)
            }
            return ApiResponse.Error(response.message())
        } catch (e: Exception) {
            return ApiResponse.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getSimilar(movieId:String): ApiResponse<MovieResponse> {
        try {
            val response = apiService.getSimilarMovies(movieId, Constant.apiKey)
            if (response.isSuccessful) {
                val creditResponse: MovieResponse = response.body() ?: return ApiResponse.Error("Credits not found")
                return ApiResponse.Success(creditResponse)
            }
            return ApiResponse.Error(response.message())
        } catch (e: Exception) {
            return ApiResponse.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getReviews(movieId:String): ApiResponse<ReviewResponse> {
        try {
            val response = apiService.getReviews(movieId, Constant.apiKey)
            if (response.isSuccessful) {
                val reviewResponse: ReviewResponse = response.body() ?: return ApiResponse.Error("Credits not found")
                return ApiResponse.Success(reviewResponse)
            }
            return ApiResponse.Error(response.message())
        } catch (e: Exception) {
            return ApiResponse.Error(e.message ?: "An unknown error occurred")
        }
    }
}
