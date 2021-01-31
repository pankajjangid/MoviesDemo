package com.momentous.movies_app.entity.network

data class MoviesDetailsResponse(
    val adult: Boolean,
    val cast: List<Cast>,
    val description: String,
    val id: Int,
    val image: String,
    val release_date: String,
    val reviews: List<Review>,
    val status: String,
    val tagline: String,
    val title: String
){


    data class Cast(
        val actor: String,
        val character: String,
        val image: String
    )

    data class Review(
        val author: String,
        val value: String
    )
}