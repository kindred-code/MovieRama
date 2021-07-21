package com.mpolitakis.movierama.networking.response.details

data class ProductionCompany(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)