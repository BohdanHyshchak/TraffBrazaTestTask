package com.example.graypart.sdk

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib

class AppsFlyer {

    fun init(context: Context) {
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                Log.d("MyApplication", "data loaded successfully :  $data")
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

        AppsFlyerLib.getInstance().init("devKey", conversionDataListener, context)
        AppsFlyerLib.getInstance().start(context)
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
