package com.example.traff.sdk

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import java.util.Objects

class AppsFlyer {

    private val devKey = "UzECrCrkUBSqGMrqUtLjh3"

    private val appsFlyerClient = AppsFlyerLib.getInstance()

    private var conversionData: MutableMap<String, Any>? = null

    fun init(context: Context) {
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                if (data != null) {
                    for (attrName in data.keys)
                        Log.d("MyApplication", "Conversion attribute: " + attrName + " = " + data.get(attrName))
                    val status = Objects.requireNonNull(data["af_status"]).toString()
                    if (status == "Non-organic") {
                        if (Objects.requireNonNull(data["is_first_launch"]).toString() == "true") {
                            Log.d("MyApplication", "Conversion: First Launch")
                        } else {
                            Log.d("MyApplication", "Conversion: Not First Launch")
                        }
                    } else {
                        Log.d("MyApplication", "Conversion: This is an organic install.")
                    }
                    conversionData = data
                }
            }
            override fun onConversionDataFail(error: String?) {
                Log.e("MyApplication", "error onAttributionFailure :  $error")
            }
            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                // Must be overriden to satisfy the AppsFlyerConversionListener interface.
                // Business logic goes here when UDL is not implemented.
                data?.map {
                    Log.d("MyApplication", "onAppOpen_attribute: ${it.key} = ${it.value}")
                }
            }
            override fun onAttributionFailure(error: String?) {
                // Must be overriden to satisfy the AppsFlyerConversionListener interface.
                // Business logic goes here when UDL is not implemented.
                Log.e("MyApplication", "error onAttributionFailure :  $error")
            }
        }

        appsFlyerClient.setDebugLog(true)
        appsFlyerClient.setMinTimeBetweenSessions(0)
        appsFlyerClient.init(devKey, conversionDataListener, context)
        appsFlyerClient.start(context)
//        appsFlyerClient.start(
//            context,
//            devKey,
//            object : AppsFlyerRequestListener {
//                override fun onSuccess() {
//                    Log.d("MyApplication", "Launch sent successfully")
//                }
//
//                override fun onError(errorCode: Int, errorDesc: String) {
//                    Log.d(
//                        "MyApplication",
//                        "Launch failed to be sent:\n" +
//                            "Error code: " + errorCode + "\n" +
//                            "Error description: " + errorDesc,
//                    )
//                }
//            },
//        )
    }
}

// TODO(): do we need AppsFlyerRequestListener?
//
//        AppsFlyerLib.getInstance().start(
//            this,
//            "Key",
//            object : AppsFlyerRequestListener {
//                override fun onSuccess() {
//                    Log.d("MyApplication", "Launch sent successfully")
//                }
//
//                override fun onError(errorCode: Int, errorDesc: String) {
//                    Log.d(
//                        "MyApplication",
//                        "Launch failed to be sent:\n" +
//                            "Error code: " + errorCode + "\n" +
//                            "Error description: " + errorDesc,
//                    )
//                }
//            }
//        )
