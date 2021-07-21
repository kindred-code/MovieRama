package com.mpolitakis.movierama.networking.response.details.reviews

data class AuthorDetails(
    val avatar_path: String,
    val name: String,
    val rating: Any,
    val username: String
)