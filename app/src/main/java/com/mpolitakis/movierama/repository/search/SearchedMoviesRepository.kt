package com.mpolitakis.movierama.repository.search

import androidx.lifecycle.LiveData
import com.mpolitakis.movierama.networking.response.search.SearchedMoviesResponse

interface SearchedMoviesRepository {

    val movieData : LiveData<SearchedMoviesResponse>
    suspend fun getSearchedMovies(page : Int, query: String)
}