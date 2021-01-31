package com.momentous.movies_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.momentous.movies_app.databinding.EmptyViewBinding
import com.momentous.movies_app.databinding.LoadingVerticalBinding
import com.momentous.movies_app.databinding.RowMovieItemBinding
import com.momentous.movies_app.entity.network.MoviesResponse
import java.util.*
import kotlin.collections.ArrayList

class MoviesAdapter(
    private var listener: (MoviesResponse.MoviesResponseItem?) -> Unit,
    private var dataList: List<MoviesResponse.MoviesResponseItem>,
    mRecyclerView: RecyclerView

    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {

        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val EMPTY = 2
    }

    private var isLoading = false
    private val visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0



    private var mOnLoadMoreListener: OnLoadMoreListener? = null

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener?) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                PopularMoviesVH(
                    RowMovieItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_LOADING -> {
                LoadingViewHolder(
                    LoadingVerticalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            EMPTY -> {
                EmptyViewHolder(
                    EmptyViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> EmptyViewHolder(
                EmptyViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        holder.apply {

            when (holder) {
                is PopularMoviesVH -> {
                    //    holder.setIsRecyclable(false)
                    val movies = dataList[position]

                    holder.onBind(movies, position)
                }
                is LoadingViewHolder -> holder.onBind()
                is EmptyViewHolder -> holder.onBind()
            }
        }


    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    override fun getItemViewType(position: Int): Int {

        return when {
            dataList[position].id!=0 -> {
                VIEW_TYPE_ITEM
            }
            dataList[position].id==0 -> {
                VIEW_TYPE_LOADING
            }
            else -> {
                EMPTY
            }
        }
    }


    fun setLoaded() {
        isLoading = false
    }

    init {
        val linearLayoutManager = mRecyclerView.layoutManager as LinearLayoutManager?
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager?.itemCount!!
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener?.onLoadMore()
                    }
                    isLoading = true
                }
            }
        })
    }


    /**
     * View Holders
     */


    inner class PopularMoviesVH(val mBinding: RowMovieItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun onBind(movie: MoviesResponse.MoviesResponseItem, postion: Int) {
            mBinding.apply {
                // mBinding.callback=callbackListner


                try {
                    setIsRecyclable(false)

                    mBinding.movie=movie
                    itemView.rootView.setOnClickListener {
                        listener(movie
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                executePendingBindings()
            }
        }


    }

    inner class LoadingViewHolder(private val mBinding: LoadingVerticalBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun onBind() {
            mBinding.apply {
                executePendingBindings()
            }
        }
    }

    inner class EmptyViewHolder(private val mBinding: EmptyViewBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun onBind() {
            mBinding.apply {
                executePendingBindings()
            }
        }
    }

}