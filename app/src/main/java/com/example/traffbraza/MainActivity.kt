package com.example.traffbraza

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.example.traffbraza.ui.Routes
import com.example.traffbraza.ui.screens.FirstScreen
import com.example.traffbraza.ui.screens.WebViewScreen
import com.example.traffbraza.ui.theme.TraffTestTheme
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {

    private val devKey = "UzECrCrkUBSqGMrqUtLjh3"

    var filePath: ValueCallback<Array<Uri>>? = null

    val getFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_CANCELED) {
            filePath?.onReceiveValue(null)
        } else if (it.resultCode == Activity.RESULT_OK && filePath != null) {
            filePath!!.onReceiveValue(
                WebChromeClient.FileChooserParams.parseResult(it.resultCode, it.data),
            )
            filePath = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val campaingCD = processConversionData(initAppsFlyer())
            Log.d("TEST", "campaingCD = $campaingCD")
        }

        val uuid = (application as MyApplication).getUUID()
        val androidID = (application as MyApplication).getAndroidID()

        Log.d("MainActivity", "uuid = $uuid, androidId = $androidID")

        setContent {
            TraffTestTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.FIRST_SCREEN) {
                    composable(Routes.FIRST_SCREEN) {
                        FirstScreen(navigation = navController)
                    }
                    composable(Routes.WEB_VIEW_SCREEN) {
                        WebViewScreen(navigation = navController, activity = this@MainActivity)
                    }
                }
            }
        }
    }

    private fun processConversionData(conversionData: MutableMap<String, Any>?) =
        if (conversionData != null) {
            if (conversionData.isNotEmpty() && conversionData["campaign"] != null && conversionData["campaign"].toString()
                    .isNotEmpty()
            ) {
                "&campaign=${conversionData["campaign"]}"
            } else {
                ""
            }
        } else {
            ""
        }
    private suspend fun initAppsFlyer() =
        suspendCoroutine<MutableMap<String, Any>?> { continuation ->
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

            AppsFlyerLib.getInstance()
                .init(devKey, conversionDataListener, this)
            AppsFlyerLib.getInstance().start(this)
        }
}
