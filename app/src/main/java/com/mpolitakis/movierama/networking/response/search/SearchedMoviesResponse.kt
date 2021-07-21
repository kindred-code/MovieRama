package com.mpolitakis.movierama.networking.response.search

data class SearchedMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)