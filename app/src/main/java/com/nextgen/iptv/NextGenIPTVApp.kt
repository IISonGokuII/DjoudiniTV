package com.nextgen.iptv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NextGenIPTVApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
