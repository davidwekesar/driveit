package com.android.driveit.application

import android.app.Application
import com.android.driveit.BuildConfig
import timber.log.Timber

class DriveItApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}