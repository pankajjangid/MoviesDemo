package com.momentous.movies_app.modules.movie_details

import com.momentous.movies_app.entity.network.MoviesDetailsResponse
import com.momentous.movies_app.entity.network.MoviesResponse
import com.momentous.movies_app.networking.MyApi
import com.momentous.movies_app.networking.SafeApiRequest

class MovieDetailsInteractor(private val api: MyApi):MoviesDetailsContract.Interactor,SafeApiRequest() {

    override suspend fun getMovieDetails(id:String): MoviesDetailsResponse {
        return apiRequest { api.getMovieDetails(id) }
    }
}