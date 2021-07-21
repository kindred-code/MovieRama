package com.mpolitakis.movierama.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpolitakis.movierama.networking.response.search.SearchedMoviesResponse
import com.mpolitakis.movierama.repository.search.SearchedMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository : SearchedMoviesRepository) : ViewModel() {


    lateinit var movieList: LiveData<SearchedMoviesResponse>


    fun getList(page: Int, query: String)  {
        viewModelScope.launch {
            repository.getSearchedMovies(page, query)

        }

        movieList = repository.movieData

    }

}