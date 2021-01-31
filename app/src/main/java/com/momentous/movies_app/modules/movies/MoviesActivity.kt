package com.momentous.movies_app.modules.movies

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.momentous.movies_app.R
import com.momentous.movies_app.adapter.MoviesAdapter
import com.momentous.movies_app.base.BaseActivity
import com.momentous.movies_app.databinding.ActivityMoviesBinding
import com.momentous.movies_app.entity.network.MoviesResponse
import com.momentous.movies_app.networking.ApiParams
import com.momentous.movies_app.utils.Logger
import kotlinx.android.synthetic.main.layout_toolbar.*


class MoviesActivity : BaseActivity<ActivityMoviesBinding>(), MoviesContract.View {

    private var searchText: String = ""
    private var sortBy: String = ""
    var searchView: SearchView? = null
    private var presenter: MoviesContract.Presenter? = null
    private var adapter: MoviesAdapter? = null

    var moviesList: ArrayList<MoviesResponse.MoviesResponseItem> = ArrayList()
    var page = 1
    var totalPages = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this, R.layout.activity_movies)

        presenter = MoviesPresenter(this)
        presenter?.onViewCreated()

        setSupportActionBar(binding.include.toolbar)
        binding.include.toolbar.title = getString(R.string.movies_list)


    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun initVars() {
        binding.showErrorLayout = false
        callApi()

        binding.btnRetry.setOnClickListener {
            callApi()
        }


    }

    override fun publishMovies(dataList: ArrayList<MoviesResponse.MoviesResponseItem>?) {
        binding.showErrorLayout = false
        if (page == 1)
            adapter = null
        if (dataList.isNullOrEmpty()) {
            // Stopping Shimmer Effect's animation after data is loaded to ListView
            binding.shimmerViewContainer.stopShimmer();
            binding.shimmerViewContainer.visibility = View.GONE;

            return;
        }

        // Stopping Shimmer Effect's animation after data is loaded to ListView
        binding.shimmerViewContainer.stopShimmer();
        binding.shimmerViewContainer.visibility = View.GONE;




        if (adapter == null) {
            moviesList.clear()


            for (i in dataList.indices) {
                if (!dataList[i].adult)
                    moviesList.add(dataList[i])

            }

            if (moviesList.isNullOrEmpty()) {
                binding.tvNotFound.visibility = View.VISIBLE;

            }

            val layoutManager =
                LinearLayoutManager(this)

            binding.rvMovies.isNestedScrollingEnabled = false

            binding.rvMovies.layoutManager = layoutManager
            adapter = MoviesAdapter(
                { result -> presenter?.onMovieSelection(result!!) },
                moviesList, binding.rvMovies
            )

            binding.rvMovies.adapter = adapter
            binding.rvMovies.itemAnimator = DefaultItemAnimator()


            adapter!!.setOnLoadMoreListener(object : MoviesAdapter.OnLoadMoreListener {
                override fun onLoadMore() {
                    if (totalPages > page) {

                        val pojo = MoviesResponse.MoviesResponseItem(
                            false,
                            "",

                            0,
                            "",
                            ""
                        )

                        moviesList.add(pojo)
                        adapter!!.notifyItemInserted(moviesList.size - 1)
                        page += 1


                        callApi()

                    }

                }
            })

        } else {
            try {

                moviesList.removeAt(moviesList.size - 1)
                adapter!!.notifyItemRemoved(moviesList.size)
                for (i in dataList.indices) {
                    if (!dataList[i].adult)
                        moviesList.add(dataList[i])

                }

                adapter!!.notifyDataSetChanged()
                adapter!!.setLoaded()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        for (i in moviesList.indices) {
            Logger.debug("Adult ${moviesList[i].adult}")
        }
    }

    override fun showLoading() {
        // showProgress()

        if (page == 1) {
            // Stopping Shimmer Effect's animation after data is loaded to ListView
            binding.shimmerViewContainer.showShimmer(true);
            binding.shimmerViewContainer.visibility = View.VISIBLE;

        }

    }

    override fun hideLoading() {
        // hideProgress()
    }

    override fun showErrorView() {
        binding.showErrorLayout = true
    }

    override fun showMessage(message: String) {
        showToast(message)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu);


        val searchViewItem = menu!!.findItem(R.id.menu_search)
        // Get the SearchView and set the searchable configuration
        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = searchViewItem.actionView as SearchView
        searchView?.queryHint = "Search by title..."
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))


        val queryTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    // This is your adapter that will be filtered

                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    // **Here you can get the value "query" which is entered in the search box.**

                    hideKeyboard()
                    searchText = query
                    resetPageCount()
                    callApi()
                    return true
                }
            }
        searchView?.setOnQueryTextListener(queryTextListener)
        return true;

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {

            R.id.menu_title_asc -> {
                sortBy = "title.asc"
                resetPageCount()
                callApi()
            }
            R.id.menu_title_desc -> {
                sortBy = "title.desc"
                resetPageCount()

                callApi()
            }

            R.id.menu_popularity_asc -> {
                sortBy = "popularity.asc"
                resetPageCount()

                callApi()
            }
            R.id.menu_popularity_desc -> {
                sortBy = "popularity.desc"
                resetPageCount()

                callApi()
            }
            R.id.menu_date_asc -> {
                sortBy = "date.asc"
                resetPageCount()

                callApi()
            }
            R.id.menu_date_desc -> {
                sortBy = "date.desc"
                resetPageCount()

                callApi()
            }
            R.id.menu_clear_all -> {
                sortBy = ""

                searchView?.onActionViewCollapsed()
                searchView?.setQuery("",false)
                searchView?.clearFocus()
                searchText=""
                resetPageCount()

                callApi()
            }

        }

        return true
    }

    private fun resetPageCount() {
        page = 1
    }

    fun callApi() {

        val request = HashMap<String, String>()

        if (searchText.isNotEmpty())
            request[ApiParams.QUERY] = searchText

        if (sortBy.isNotEmpty())
            request[ApiParams.SORT] = sortBy

        request[ApiParams.PAGE] = page.toString()
        presenter?.fetchMovies(request)
    }
}