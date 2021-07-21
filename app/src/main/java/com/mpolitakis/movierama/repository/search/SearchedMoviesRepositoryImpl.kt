package com.mpolitakis.movierama.repository.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mpolitakis.movierama.internal.Constants
import com.mpolitakis.movierama.networking.ApiService
import com.mpolitakis.movierama.networking.response.search.SearchedMoviesResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.IOException
import javax.inject.Inject

class SearchedMoviesRepositoryImpl @Inject constructor(private val api: ApiService): SearchedMoviesRepository {

    private var _movieData = MutableLiveData<SearchedMoviesResponse>()
    override val movieData: LiveData<SearchedMoviesResponse>
        get() { return _movieData }

    @DelicateCoroutinesApi
    override suspend fun getSearchedMovies(page: Int, query: String) {
        try {
            _movieData.postValue(  api.getSearchedMovies(Constants.apiKey, query, page))
        }
        catch (e: IOException){
            Log.e("Network", "No network")
        }

    }
}