package com.mpolitakis.movierama.networking.response.details.similar

data class SimilarMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)