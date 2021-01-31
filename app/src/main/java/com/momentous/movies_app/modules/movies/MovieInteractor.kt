package com.momentous.movies_app.modules.movies

import com.momentous.movies_app.entity.network.MoviesResponse
import com.momentous.movies_app.networking.MyApi
import com.momentous.movies_app.networking.SafeApiRequest

class MovieInteractor(private val api: MyApi):MoviesContract.Interactor,SafeApiRequest() {
    override suspend fun getMovies(request: HashMap<String, String>): MoviesResponse {

        return apiRequest { api.getMovies(request) }

    }
}