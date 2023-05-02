package com.example.traff.ui.screens

import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.traff.MainActivity
import com.example.traff.ui.Routes
import com.example.traff.util.ChromeClient

@Composable
fun WebViewScreen(activity: MainActivity, navigation: NavController) {
    Scaffold(
        content = { padding ->
            WebView(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                activity,
                navigation,
            )
        },
    )
}

@Composable
fun WebView(modifier: Modifier = Modifier, activity: MainActivity, navigation: NavController) {
    // Declare a string that contains a url
    val mUrl = "https://google.com"
    var webView: WebView? by remember { // TODO: investigate with back button
        mutableStateOf(null)
    }

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                // Enable cookies
                val cookieManager: CookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)

                // Enable third-party cookies
                cookieManager.setAcceptThirdPartyCookies(this, true)

                // Configure WebView settings
                val settings: WebSettings = this.settings
                settings.javaScriptEnabled = true // TODO: investigate
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = ChromeClient(activity)
                loadUrl(mUrl)
                webView = this
            }
        },
        update = {
            it.loadUrl(mUrl)
        },
    )

    BackHandler(enabled = true) {
        Log.d("TEST", "JUST")
        if (webView == null) {
            Log.d("TEST", "webView == null")
        }
        webView?.let { webView ->
            if (webView.canGoBack()) {
                webView.goBack()
                Log.d("TEST", "webView.goBack()")
            } else {
                // If WebView cannot go back, allow the system back navigation
                navigation.navigate(Routes.FIRST_SCREEN)
                Log.d("TEST", "navigation.navigate(Routes.FIRST_SCREEN)")
            }
        }
    }
}
