package com.mpolitakis.movierama.networking


import com.mpolitakis.movierama.networking.response.popular.PopularMoviesResponse
import com.mpolitakis.movierama.networking.response.search.SearchedMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") key: String, @Query("page") page: Int): PopularMoviesResponse

    @GET("search/movie")
    suspend fun getSearchedMovies(@Query("api_key") key: String,
                                  @Query("query") query: String, @Query("page")page: Int
                                  ) : SearchedMoviesResponse
}
