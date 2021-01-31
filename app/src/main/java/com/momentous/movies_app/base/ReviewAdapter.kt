package com.momentous.movies_app.base

import com.momentous.movies_app.R
import com.momentous.movies_app.databinding.RowCastItemBinding
import com.momentous.movies_app.databinding.RowReviewItemBinding
import com.momentous.movies_app.entity.network.MoviesDetailsResponse

class ReviewAdapter : BaseRVAdapter<MoviesDetailsResponse.Review, RowReviewItemBinding>() {
    override fun getLayout(): Int = R.layout.row_review_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<RowReviewItemBinding>,
        position: Int
    ) {

        holder.binding.review = items[position]
    }
}