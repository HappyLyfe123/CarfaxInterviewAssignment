package com.carfax.carfax_interview_assignment

import android.app.Application
import carfaxInterviewAssignment.BuildConfig
import carfaxInterviewAssignment.R
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.memory.MemoryCache
import coil.request.CachePolicy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CarfaxInterviewAssignmentApplication : Application(), ImageLoaderFactory{


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this).run {
            crossfade(true)
            placeholder(R.drawable.default_image_placeholder)
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
            fallback(com.carfax.feature_vehicle_listing.R.drawable.default_image_placeholder)
            error(com.carfax.feature_vehicle_listing.R.drawable.default_image_placeholder)
            build()
        }
    }

}
