package com.momentous.movies_app.modules.splash

import com.momentous.movies_app.utils.App


class SplashPresenter(private var view: SplashContract.View?) : SplashContract.Presenter {

    var router: SplashContract.Router? = SplashRouter(view as SplashActivity)

    override fun onViewCreated() {
        view?.onViewCreated()


    }

    override fun checkLoginStatus() {
        //We were write the logic the that user is login or not

        router?.launchMainScreen()
        view?.finishView()
    }

    override fun onViewDestroyed() {

        view = null
    }

}