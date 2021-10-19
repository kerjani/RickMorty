package com.kernacs.rickmorty.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kernacs.rickmorty.R

@BindingAdapter("app:loadImage")
fun loadImage(
    imageView: ImageView,
    imageUrl: String?,
) {
    imageUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView)
            .load(imgUri)
            .transform()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_downloading)
                    .error(R.drawable.ic_error)
            )
            .into(imageView)
    }
}


@BindingAdapter("statusColor")
fun statusColor(view: TextView, status: String?) {
    val color = when {
        "unknown".equals(status, true) -> {
            R.color.status_unknown
        }
        "Dead".equals(status, true) -> {
            R.color.status_dead
        }
        else -> {
            R.color.status_alive
        }
    }

    view.setTextColor(color)
}