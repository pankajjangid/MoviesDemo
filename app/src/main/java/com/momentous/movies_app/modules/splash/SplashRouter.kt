package com.momentous.movies_app.modules.splash

import com.momentous.movies_app.modules.movies.MoviesActivity
import com.momentous.movies_app.utils.Utils

class SplashRouter(private val activity: SplashActivity) : SplashContract.Router {


    override fun launchMainScreen() {
        Utils.startNewActivity(activity, MoviesActivity::class.java, true)
    }
}