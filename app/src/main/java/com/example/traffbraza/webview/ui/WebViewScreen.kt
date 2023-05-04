package com.example.traffbraza.webview.ui

import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.traffbraza.MainActivity
import com.example.traffbraza.webview.util.ChromeClient
import com.example.traffbraza.webview.util.WebClient

@Composable
fun WebViewScreen(
    activity: MainActivity,
    navigation: NavController,
    viewModel: WebViewVM = hiltViewModel(),
    urlParameters: String = "",
) {
    Scaffold(
        content = { padding ->
            WebView(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                activity,
                navigation,
                viewModel,
                urlParameters,
            )
        },
    )
}

@Composable
fun WebView(
    modifier: Modifier = Modifier,
    activity: MainActivity,
    navigation: NavController,
    viewModel: WebViewVM,
    urlParameters: String?,
) {
    // Declare a string that contains a url
    val mUrl = viewModel.lastUrl
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
                webViewClient = WebClient(viewModel::saveLastUrl, urlParameters)
                webChromeClient = ChromeClient(activity)
                loadUrl(mUrl)
                webView = this
            }
        },
    ) {
        it.loadUrl(mUrl)
    }

    BackHandler(enabled = true) {
        webView?.let { webView ->
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                // If WebView cannot go back, allow the system back navigation
                navigation.popBackStack()
            }
        }
    }
}
