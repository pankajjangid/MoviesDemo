package com.momentous.movies_app.modules.splash

interface SplashContract {

    interface View {

        fun onViewCreated()
        fun finishView()
    }

    interface Presenter {
        fun onViewCreated()
        fun checkLoginStatus()
        fun onViewDestroyed()
    }

    interface Router{

        fun launchMainScreen()
    }
}