package com.mpolitakis.movierama.repository.details


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mpolitakis.movierama.internal.Constants
import com.mpolitakis.movierama.networking.ApiService
import com.mpolitakis.movierama.networking.response.details.MovieDetailsResponse
import com.mpolitakis.movierama.networking.response.details.reviews.MovieReviewsResponse
import com.mpolitakis.movierama.networking.response.details.similar.SimilarMoviesResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.IOException
import javax.inject.Inject


class MovieDetailsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MovieDetailsRepository {


    private val _movieData: MutableLiveData<MovieDetailsResponse> =
        MutableLiveData<MovieDetailsResponse>()
    override val movieData: LiveData<MovieDetailsResponse>
        get() {
            return _movieData
        }


    @DelicateCoroutinesApi
    override suspend fun getMovieDetails(movieId: Int) {

        try{
            _movieData.postValue(api.getMovieDetails(movieId, Constants.apiKey))
        }
        catch (e: IOException){
            Log.e("Network", "No network")
        }


    }

    private val _similarMoviesData: MutableLiveData<SimilarMoviesResponse> =
        MutableLiveData<SimilarMoviesResponse>()
    override val similarMoviesData : LiveData<SimilarMoviesResponse>
        get() {
            return _similarMoviesData
        }


    @DelicateCoroutinesApi
    override suspend fun getSimilarMovies(movieId: Int) {

        try{
            _similarMoviesData.postValue(api.getSimilarMovies(movieId, Constants.apiKey))
        }
        catch (e: IOException){
            Log.e("Network", "No network")
        }


    }

    private val _reviewsData: MutableLiveData<MovieReviewsResponse> =
        MutableLiveData<MovieReviewsResponse>()
    override val reviewsData : LiveData<MovieReviewsResponse>
        get() {
            return _reviewsData
        }


    @DelicateCoroutinesApi
    override suspend fun getReviews(movieId: Int) {
        try{
            _reviewsData.postValue(api.getReviews(movieId, Constants.apiKey))
        }
        catch (e: IOException){
            Log.e("Network", "No network")
        }

    }





}