package com.momentous.movies_app.entity.network

class MoviesResponse : ArrayList<MoviesResponse.MoviesResponseItem>(){


    data class MoviesResponseItem(
        val adult: Boolean,
        val description: String,
        val id: Int,
        val image: String,
        val title: String
    )
}