package com.mpolitakis.movierama.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpolitakis.movierama.networking.response.popular.PopularMoviesResponse
import com.mpolitakis.movierama.repository.popular.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MainViewModel @Inject constructor(private val repository : PopularMoviesRepository) : ViewModel() {


    lateinit var movieList: LiveData<PopularMoviesResponse>


    fun getList(page: Int)  {
        viewModelScope.launch {
            repository.getPopularMovies(page)

        }

        movieList = repository.movieData

    }

}