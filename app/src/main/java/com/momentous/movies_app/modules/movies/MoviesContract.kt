package com.momentous.movies_app.modules.movies

import android.icu.text.CaseMap
import com.momentous.movies_app.entity.network.MoviesResponse

interface MoviesContract {


    interface View {

        fun initVars()
        fun publishMovies(
            moviesList: ArrayList<MoviesResponse.MoviesResponseItem>
            ?
        )

        fun showLoading()
        fun hideLoading()
        fun showErrorView()
        fun showMessage(message: String)

    }

    interface Presenter {
        //model update
        fun onViewCreated()
        fun onViewDestroyed()

        //user event
        fun fetchMovies(request: HashMap<String, String>)
        fun onMovieSelection(movie: MoviesResponse.MoviesResponseItem)
    }

    interface Interactor {

        suspend fun getMovies(request: HashMap<String, String>): MoviesResponse


    }

    interface Router {

        fun launchMovieDetails(movie_id: Int,title: String)
    }
}