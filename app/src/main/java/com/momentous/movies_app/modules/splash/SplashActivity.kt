package com.momentous.movies_app.modules.splash

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.momentous.movies_app.R
import com.momentous.movies_app.base.BaseActivity
import com.momentous.movies_app.databinding.ActivitySplashBinding
import com.momentous.movies_app.utils.Coroutines
import kotlinx.coroutines.delay

class SplashActivity : BaseActivity<ActivitySplashBinding>(), SplashContract.View {
    private var presenter: SplashPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this,R.layout.activity_splash)
        transparentStatusBar()

        presenter = SplashPresenter(this)

    }

    override fun onResume() {
        super.onResume()

        presenter?.onViewCreated()

    }


    override fun onViewCreated() {

        Coroutines.main {

            delay(2000)

            presenter?.checkLoginStatus()
        }
    }

    override fun finishView() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onViewDestroyed()
        presenter = null
    }


    override fun getToolbarInstance(): Toolbar? = null
}