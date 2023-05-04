package com.example.traffbraza

import android.app.Application
import com.example.traffbraza.webview.sdk.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.init(this)
    }
}
