package com.momentous.movies_app.modules.movie_details

import com.momentous.movies_app.modules.movies.MovieInteractor
import com.momentous.movies_app.modules.movies.MoviesActivity
import com.momentous.movies_app.networking.MyApi
import com.momentous.movies_app.utils.Coroutines
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MovieDetailsPresenter(private var view: MoviesDetailsContract.View?): MoviesDetailsContract.Presenter ,
    KodeinAware {

    private val api: MyApi by instance()

    private val interactor: MovieDetailsInteractor by lazy {
        MovieDetailsInteractor(api)
    }
    override fun onViewCreated() {
        view?.initVars()


    }

    override fun onViewDestroyed() {
        view = null

    }

    override fun fetchMovieDetails(id: String) {

        view?.showLoading()

        Coroutines.main {
            try {

                val response = interactor.getMovieDetails(id)


                view?.hideLoading()
                view?.publishMovieDetails(response)

            } catch (e: Exception) {
                view?.hideLoading()

                e.printStackTrace()
            }
        }
    }
    override val kodein: Kodein by kodein(view as MovieDetailsActivity)

}