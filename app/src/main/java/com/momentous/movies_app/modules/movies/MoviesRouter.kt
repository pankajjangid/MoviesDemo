package com.momentous.movies_app.modules.movies

import android.content.Intent
import com.momentous.movies_app.modules.movie_details.MovieDetailsActivity
import com.momentous.movies_app.networking.ApiParams

class MoviesRouter(private val activity: MoviesActivity) : MoviesContract.Router {
    override fun launchMovieDetails(movie_id: Int, title: String) {

        val intent = Intent(activity, MovieDetailsActivity::class.java)

        intent.putExtra(ApiParams.ID, movie_id.toString())
        intent.putExtra(ApiParams.TITLE, title)

        activity.startActivity(intent)
    }
}