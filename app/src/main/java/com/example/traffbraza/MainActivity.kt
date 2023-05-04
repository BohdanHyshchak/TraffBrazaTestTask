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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.traffbraza.game.GameRoute
import com.example.traffbraza.ui.screens.FirstScreen
import com.example.traffbraza.ui.theme.TraffTestTheme
import com.example.traffbraza.webview.sdk.AppsFlyer
import com.example.traffbraza.webview.ui.WebViewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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

        setContent {
            val scope = rememberCoroutineScope()
            val isAllowed = remember { mutableStateOf(false) }
            val urlParams = remember { mutableStateOf("") }
            TraffTestTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NavRoute.FirstScreen.path) {
                    composable(NavRoute.FirstScreen.path) {
                        FirstScreen(navigation = navController, isAllowed, urlParams)
                    }
                    composable(
                        NavRoute.WebViewScreen.path + "/{urlParams}",
                        arguments = listOf(
                            navArgument("urlParams") {
                                type = NavType.StringType
                                defaultValue = ""
                                nullable = false
                            },
                        ),
                    ) {
                        WebViewScreen(
                            navigation = navController,
                            activity = this@MainActivity,
                            urlParameters = it.arguments?.getString("urlParams") ?: "",
                        )
                    }
                    composable(NavRoute.GameScreen.path) {
                        GameRoute()
                    }
                }
            }
            LaunchedEffect(true) {
                val appsFlyerParams = processConversionData(AppsFlyer.initAndGetConversionData(this@MainActivity))
                Log.d("TEST", "appsFlyerParams = $appsFlyerParams")
                isAllowed.value = true
                urlParams.value = appsFlyerParams
            }
        }
    }

    private fun processConversionData(conversionData: MutableMap<String, Any>?): String {
        if (!conversionData.isNullOrEmpty() && conversionData["campaign"] != null) {
            val campaign = conversionData["campaign"].toString()
            if (campaign.isNotEmpty()) {
                return "&campaign=$campaign"
            }
        }
        return ""
    }
}

//          For future
//        lifecycleScope.launch(Dispatchers.IO) {
//            val advertisingID = try {
//                AdvertisingIdClient.getAdvertisingIdInfo(this@MainActivity.applicationContext).id.toString()
//            } catch (exception: Exception) {
//                "null"
//            }
//            Log.d("TEST", "advertisingID = $advertisingID")
//        }
//        val uuid = (application as MyApplication).getUUID()
//        val androidID = (application as MyApplication).getAndroidID()
