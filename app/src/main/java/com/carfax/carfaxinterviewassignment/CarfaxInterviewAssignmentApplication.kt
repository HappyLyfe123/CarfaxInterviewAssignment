package com.carfax.carfaxinterviewassignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CarfaxInterviewAssignmentApplication : Application(){

    companion object{
        lateinit var instance: CarfaxInterviewAssignmentApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}
