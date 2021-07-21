package com.mpolitakis.movierama.repository.popular


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mpolitakis.movierama.internal.Constants
import com.mpolitakis.movierama.networking.ApiService
import com.mpolitakis.movierama.networking.response.popular.PopularMoviesResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.IOException
import javax.inject.Inject


class PopularMoviesRepositoryImpl @Inject constructor(
    private val api: ApiService
) : PopularMoviesRepository {


    private val _moviesData: MutableLiveData<PopularMoviesResponse> =
        MutableLiveData<PopularMoviesResponse>()
    override val movieData: LiveData<PopularMoviesResponse>
        get() {
            return _moviesData
        }


    @DelicateCoroutinesApi
    override suspend fun getPopularMovies(page: Int) {

        try{
            _moviesData.postValue(api.getPopularMovies(Constants.apiKey, page))
        }
        catch (e: IOException){
            Log.e("Network", "No network")
        }


    }


}