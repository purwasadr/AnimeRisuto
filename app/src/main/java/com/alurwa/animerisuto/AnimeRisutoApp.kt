package com.alurwa.animerisuto

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

@HiltAndroidApp
class AnimeRisutoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
