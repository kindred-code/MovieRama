package com.mpolitakis.movierama.networking.response.details.reviews

data class MovieReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)