package com.momentous.movies_app.base

import com.momentous.movies_app.R
import com.momentous.movies_app.databinding.RowCastItemBinding
import com.momentous.movies_app.entity.network.MoviesDetailsResponse

class CastAdapter : BaseRVAdapter<MoviesDetailsResponse.Cast, RowCastItemBinding>() {
    override fun getLayout(): Int = R.layout.row_cast_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<RowCastItemBinding>,
        position: Int
    ) {

        holder.binding.cast = items[position]
    }
}