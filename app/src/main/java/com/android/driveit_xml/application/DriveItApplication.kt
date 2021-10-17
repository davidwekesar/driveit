package com.android.driveit_xml.application

import android.app.Application
import com.android.driveit_xml.BuildConfig
import timber.log.Timber

class DriveItApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}