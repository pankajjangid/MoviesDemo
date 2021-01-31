package com.momentous.movies_app.modules.movie_details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import com.momentous.movies_app.R
import com.momentous.movies_app.base.BaseActivity
import com.momentous.movies_app.base.CastAdapter
import com.momentous.movies_app.base.ReviewAdapter
import com.momentous.movies_app.databinding.ActivityMovieDetailsBinding
import com.momentous.movies_app.entity.network.MoviesDetailsResponse
import com.momentous.movies_app.networking.ApiParams
import kotlinx.android.synthetic.main.layout_toolbar.*

class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding>(),
    MoviesDetailsContract.View {


    private var movie_id: String = ""
    private var title: String = ""
    private var presenter: MoviesDetailsContract.Presenter? = null
    private val castAdapter by lazy { CastAdapter() }
    private val reviewAdapter by lazy { ReviewAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this, R.layout.activity_movie_details)

        movie_id = intent.getStringExtra(ApiParams.ID).toString()
        title = intent.getStringExtra(ApiParams.TITLE).toString()
        presenter = MovieDetailsPresenter(this)
        presenter?.onViewCreated()
    }

    override fun initVars() {

        toolbar.title=title
        presenter?.fetchMovieDetails(movie_id)


        binding.tabs.addTab(binding.tabs.newTab().setText(getString(R.string.cast)));
        binding.tabs.addTab(binding.tabs.newTab().setText(getString(R.string.review)));
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {


            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.position) {
                    0 -> {
                        binding.llCast.visibility =View.VISIBLE
                        binding.llReview.visibility =View.GONE
                    }
                    1 -> {

                        binding.llCast.visibility =View.GONE
                        binding.llReview.visibility =View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        });


    }

    override fun publishMovieDetails(moviesDetails: MoviesDetailsResponse?) {
        binding.clMain.visibility = View.VISIBLE

        binding.movie = moviesDetails

        if (moviesDetails == null || moviesDetails.cast.isNullOrEmpty()) {
            binding.tvNotFoundCast.visibility = View.VISIBLE
            binding.rvCast.visibility = View.GONE
        } else {
            binding.tvNotFoundCast.visibility = View.GONE
            binding.rvCast.visibility = View.VISIBLE
            binding.rvCast.adapter = castAdapter
            castAdapter.addItems(moviesDetails.cast)

        }
        if (moviesDetails == null || moviesDetails.reviews.isNullOrEmpty()) {
            binding.tvNotFoundReview.visibility = View.VISIBLE
            binding.rvReview.visibility = View.GONE


        }else{
            binding.tvNotFoundReview.visibility = View.GONE
            binding.rvReview.visibility = View.VISIBLE


            binding.rvReview.adapter = reviewAdapter
            reviewAdapter.addItems(moviesDetails.reviews)
        }
    }

    override fun showLoading() {

        showProgress()
    }

    override fun hideLoading() {

        hideProgress()
    }

    override fun showMessage(message: String) {
        showToast(message)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
    override fun getToolbarInstance(): Toolbar? = toolbar
}