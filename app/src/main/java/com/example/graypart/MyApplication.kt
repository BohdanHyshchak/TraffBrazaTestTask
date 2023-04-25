package com.example.graypart

import android.app.Application
import android.provider.Settings
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.example.graypart.sdk.AppsFlyer
import com.example.graypart.sdk.OneSignal

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val uuid = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        val androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("MyApplication", "uuid = $uuid, androidId = $androidId")

        val appsFlyerClient = AppsFlyer()
        val oneSignalClient = OneSignal()

        appsFlyerClient.init(this)
        oneSignalClient.init(this)
    }
}
