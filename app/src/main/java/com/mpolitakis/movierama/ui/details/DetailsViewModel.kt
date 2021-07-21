package com.mpolitakis.movierama.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpolitakis.movierama.networking.response.details.MovieDetailsResponse
import com.mpolitakis.movierama.networking.response.details.reviews.MovieReviewsResponse
import com.mpolitakis.movierama.networking.response.details.similar.SimilarMoviesResponse
import com.mpolitakis.movierama.repository.details.MovieDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository : MovieDetailsRepository) : ViewModel() {


    lateinit var movieDetails: LiveData<MovieDetailsResponse>
    lateinit var moviesSimilar: LiveData<SimilarMoviesResponse>
    lateinit var movieReviews : LiveData<MovieReviewsResponse>

    fun getDetails(movieId: Int)  {
        viewModelScope.launch {
            repository.getMovieDetails(movieId)

        }

        movieDetails = repository.movieData

    }

    fun getSimilar(movieId: Int){
        viewModelScope.launch {
        repository.getSimilarMovies(movieId)}
        moviesSimilar = repository.similarMoviesData
    }

    fun getReviews(movieId: Int){
        viewModelScope.launch {
            repository.getReviews(movieId)
        }
        movieReviews = repository.reviewsData
    }


}