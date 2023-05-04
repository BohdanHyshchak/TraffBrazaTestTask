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

// For Future
// for (attrName in data.keys)
// Log.d("MyApplication", "Conversion attribute: " + attrName + " = " + data[attrName])
// val status = Objects.requireNonNull(data["af_status"]).toString()
// if (status == "Non-organic") {
//    if (Objects.requireNonNull(data["is_first_launch"]).toString() == "true") {
//        Log.d("MyApplication", "Conversion: First Launch")
//    } else {
//        Log.d("MyApplication", "Conversion: Not First Launch")
//    }
// } else {
//    Log.d("MyApplication", "Conversion: This is an organic install.")
// }
// conversionData = data
