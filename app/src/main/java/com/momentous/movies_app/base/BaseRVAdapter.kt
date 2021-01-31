package com.momentous.movies_app.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRVAdapter<T : Any, VB : ViewDataBinding>
    : RecyclerView.Adapter<BaseRVAdapter.Companion.BaseViewHolder<VB>>() {
    private var isLoading = false


    var items = mutableListOf<T>()

    fun addItems(items: List<T>) {
        val mutableList = items as MutableList<T>
        val size = mutableList.size
        this.items.addAll(mutableList)
        val sizeNew = this.items.size
        notifyItemRangeChanged(size, sizeNew)
    }
    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)    }

    var listener: ((view: View, item: T, position: Int) -> Unit)? = null

    abstract fun getLayout(): Int

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB>(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayout(),
            parent,
            false
        )
    )




    companion object {


        class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) :
            RecyclerView.ViewHolder(binding.root)
    }


}