package com.momentous.movies_app.modules.movie_details

import com.momentous.movies_app.entity.network.MoviesDetailsResponse

interface MoviesDetailsContract {

    interface View {

        fun initVars()
        fun publishMovieDetails(
            moviesDetails:MoviesDetailsResponse
            ?
        )

        fun showLoading()
        fun hideLoading()
        fun showMessage(message: String)

    }

    interface Presenter {
        //model update
        fun onViewCreated()
        fun onViewDestroyed()

        //user event
        fun fetchMovieDetails(id:String)
    }

    interface Interactor {

        suspend fun getMovieDetails(id:String): MoviesDetailsResponse


    }


    interface Router{

    }
}