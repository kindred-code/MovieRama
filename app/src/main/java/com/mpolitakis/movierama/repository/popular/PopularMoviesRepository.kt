package com.mpolitakis.movierama.repository.popular

import androidx.lifecycle.LiveData
import com.mpolitakis.movierama.networking.response.popular.PopularMoviesResponse


interface PopularMoviesRepository {

        val movieData : LiveData<PopularMoviesResponse>
        suspend fun getPopularMovies(page : Int)

}