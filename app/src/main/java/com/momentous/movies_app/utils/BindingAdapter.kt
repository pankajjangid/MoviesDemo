package com.momentous.movies_app.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.momentous.movies_app.BuildConfig
import com.momentous.movies_app.R
import com.momentous.movies_app.networking.ApiParams
import de.hdodenhof.circleimageview.CircleImageView


object BindingAdapter {
    val logger = Logger("CustomSetter")
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)

    @JvmStatic
    @BindingAdapter("android:loadImage")
    fun loadImage(view: ImageView, imageUrl: String?) {
        logger.debug(imageUrl + "")

        /*     val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

    // This is the placeholder for the imageView
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
    */

        var url = ""
        url = if (!imageUrl.isNullOrEmpty() && imageUrl.startsWith("http")) {
            imageUrl
        } else {
            BuildConfig.IMAGE_URL + imageUrl + "?size=w154"
        }

        val glideUrl = GlideUrl(
            url,

            LazyHeaders.Builder()
                .addHeader(ApiParams.API_KEY, BuildConfig.API_KEY)
                .build()
        )

        if (!imageUrl.isNullOrEmpty()) {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
            // .placeholder(R.drawable.ic_placeholder)

            Glide.with(view.context)
                .load(glideUrl).apply(requestOptions)
                .dontAnimate()
                .into(view)
        } else {
            /*  Glide.with(view.context)

                  .load(R.drawable.no_image).apply(RequestOptions())
                  .dontAnimate()

                  .into(view)*/
        }

    }

    @JvmStatic
    @BindingAdapter("android:loadDrawable")
    fun loadDrawable(view: ImageView, image: Int?) {
        try {
            if (image != null) {
                view.setImageResource(image)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("android:loadCircleImage")
    fun loadCircleImage(view: CircleImageView, imageUrl: String?) {

        var url = ""
        url = if (!imageUrl.isNullOrEmpty() && imageUrl.startsWith("http")) {
            imageUrl
        } else {
            BuildConfig.IMAGE_URL + imageUrl
        }

        logger.debug(imageUrl + "")
        if (!imageUrl.isNullOrEmpty()) {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
            // .placeholder(R.drawable.ic_placeholder)

            Glide.with(view.context)
                .load(url).apply(requestOptions)
                .dontAnimate()
                .into(view)
        } else {
            /* Glide.with(view.context)

                 .load(R.drawable.no_image).apply(RequestOptions())
                 .dontAnimate()

                 .into(view)*/
        }

    }

    @JvmStatic
    @BindingAdapter("android:setOnBoardImage")
    fun setOnBoardImage(view: ConstraintLayout, imageUrl: String) {
        view.visibility = View.GONE
        var url = ""
        url = if (imageUrl.startsWith("http")) {
            imageUrl
        } else {
            BuildConfig.IMAGE_URL + imageUrl
        }
        Glide.with(view.context).asBitmap().apply(requestOptions).load(url)
            .thumbnail(0.1f)
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {

                    val d: Drawable = BitmapDrawable(view.context.resources, resource)
                    view.visibility = View.VISIBLE
                    view.background = d
                }

            });


    }

    @JvmStatic
    @BindingAdapter("fixTextBig")
    fun setBigText(textView: TextView, text: String?) {

        if (text.isNullOrEmpty())
            textView.text = textView.context.getString(R.string.na)
        else {
            if (text.length < 150) {
                textView.text = text
            } else {
                val s = text.take(150) + "..."
                textView.text = s
            }
        }

    }

    @JvmStatic
    @BindingAdapter("checkText")
    fun checkText(textView: TextView, text: String?) {

        if (text.isNullOrEmpty())
            textView.text = textView.context.getString(R.string.na)
        else {
            textView.text = text

        }

    }
}