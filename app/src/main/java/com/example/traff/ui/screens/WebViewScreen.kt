package com.example.traff.ui.screens

import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.traff.MainActivity
import com.example.traff.util.ChromeClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(activity: MainActivity) {
//    val context = LocalContext.current
    Scaffold(
        content = { padding -> WebViewScreen(padding, activity) },
    )
}

@Composable
fun WebViewScreen(padding: PaddingValues, activity: MainActivity) {
    // Declare a string that contains a url
    val mUrl = "https://google.com"
    var webView: WebView? = null

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
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

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            webView?.let { webView ->
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    // If WebView cannot go back, allow the system back navigation
                    isEnabled = false
                    activity.onBackPressed()
                }
            }
        }
    }

    activity.onBackPressedDispatcher.addCallback(onBackPressedCallback)
}
