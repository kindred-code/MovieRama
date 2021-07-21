package com.mpolitakis.movierama.networking.response.details

data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>
)