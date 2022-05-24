package com.carfax.library_network

import android.content.Context
import android.widget.ImageView
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.ViewSizeResolver

fun Context.preLoadImageMemoryCache(url: String, imageView: ImageView?) {
    val request = ImageRequest.Builder(this).run {
        data(url)
        if (imageView != null) {
            size(ViewSizeResolver(imageView))
        }
        build()
    }
    imageLoader.enqueue(request)
}