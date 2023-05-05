package com.example.traffbraza.webview.sdk

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import kotlin.coroutines.suspendCoroutine

object AppsFlyer {

    private const val devKey = "PDgB82frLkGuFghq5T5EyG"

    private val appsFlyerClient = AppsFlyerLib.getInstance()

    suspend fun initAndGetConversionData(context: Context) = suspendCoroutine { continuation ->
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                continuation.resumeWith(Result.success(data))
            }
            override fun onConversionDataFail(error: String?) {
                continuation.resumeWith(Result.success(null))
            }
            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) = Unit
            override fun onAttributionFailure(error: String?) = Unit
        }
        appsFlyerClient.setDebugLog(true)
        appsFlyerClient.setMinTimeBetweenSessions(0)
        appsFlyerClient.init(devKey, conversionDataListener, context)
        appsFlyerClient.start(context)
    }
}
