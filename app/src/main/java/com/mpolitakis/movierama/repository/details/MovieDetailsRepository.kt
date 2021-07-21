package com.mpolitakis.movierama.repository.details

import androidx.lifecycle.LiveData
import com.mpolitakis.movierama.networking.response.details.MovieDetailsResponse
import com.mpolitakis.movierama.networking.response.details.reviews.MovieReviewsResponse
import com.mpolitakis.movierama.networking.response.details.similar.SimilarMoviesResponse


interface MovieDetailsRepository {

        val movieData : LiveData<MovieDetailsResponse>
        suspend fun getMovieDetails(movieId : Int)
        val similarMoviesData : LiveData<SimilarMoviesResponse>
        suspend fun getSimilarMovies(movieId: Int)
        val reviewsData : LiveData<MovieReviewsResponse>
        suspend fun getReviews(movieId: Int)

}