package com.mpolitakis.movierama.networking.response.popular



data class PopularMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int

)