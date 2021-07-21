package com.mpolitakis.movierama.networking


import com.mpolitakis.movierama.networking.response.details.MovieDetailsResponse
import com.mpolitakis.movierama.networking.response.details.reviews.MovieReviewsResponse
import com.mpolitakis.movierama.networking.response.details.similar.SimilarMoviesResponse
import com.mpolitakis.movierama.networking.response.popular.PopularMoviesResponse
import com.mpolitakis.movierama.networking.response.search.SearchedMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") key: String, @Query("page") page: Int): PopularMoviesResponse

    @GET("search/movie")
    suspend fun getSearchedMovies(@Query("api_key") key: String,
                                  @Query("query") query: String, @Query("page")page: Int
                                  ) : SearchedMoviesResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int, @Query("api_key") key: String,
                                @Query("append_to_response") append_to_response : String = "credits")
    : MovieDetailsResponse


    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(@Path("movieId") movieId: Int,
                                 @Query("api_key") key: String) : SimilarMoviesResponse

    @GET("movie/{movieId}/reviews")
    suspend fun getReviews(@Path("movieId") movieId: Int,
                           @Query("api_key") key: String) : MovieReviewsResponse
}
