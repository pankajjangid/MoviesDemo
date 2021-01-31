package com.momentous.movies_app.modules.movies

import com.momentous.movies_app.entity.network.MoviesResponse
import com.momentous.movies_app.networking.MyApi
import com.momentous.movies_app.utils.Coroutines
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MoviesPresenter(private var view: MoviesContract.View?) : MoviesContract.Presenter,
    KodeinAware {

    private val api: MyApi by instance()

    private var router: MoviesContract.Router? = MoviesRouter(view as MoviesActivity)

    private val selectionInteractor: MovieInteractor by lazy {
        MovieInteractor(api)
    }

    override fun onViewCreated() {

        view?.initVars()

    }

    override fun onViewDestroyed() {
        view = null
    }

    override fun fetchMovies(request: HashMap<String, String>) {

        view?.showLoading()

        Coroutines.main {
            try {

                val response = selectionInteractor.getMovies(request)

                if (!response.isNullOrEmpty()) {


                    view?.hideLoading()
                    view?.publishMovies(response)
                } else {
                    view?.hideLoading()
                }

            } catch (e: Exception) {
                view?.hideLoading()
                view?.publishMovies(null)
                view?.showErrorView()
                e.printStackTrace()
            }
        }

    }

    override fun onMovieSelection(movie: MoviesResponse.MoviesResponseItem) {

        router?.launchMovieDetails(movie_id = movie.id, movie.title)
    }

    override val kodein: Kodein by kodein(view as MoviesActivity)

}