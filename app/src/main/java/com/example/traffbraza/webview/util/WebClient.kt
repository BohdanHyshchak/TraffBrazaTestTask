package com.example.traffbraza.webview.util

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class WebClient(private val onSaveUrl: (String?) -> Unit, private val urlParameters: String?) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onSaveUrl(url)

        if (url != null) {
            if (url.contains("fex.net", true)) {
                Log.d("WebClient", "Campaign from Apps: $urlParameters")
            }
        }
    }
}
