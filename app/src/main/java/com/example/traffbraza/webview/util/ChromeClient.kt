package com.example.traffbraza.webview.util

import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.traffbraza.MainActivity

class ChromeClient(private val myActivity: MainActivity) : WebChromeClient() {
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?,
    ): Boolean {
        myActivity.filePath = filePathCallback

        val contentIntent = Intent(Intent.ACTION_GET_CONTENT)
        contentIntent.type = "*/*"
        contentIntent.addCategory(Intent.CATEGORY_OPENABLE)

        myActivity.getFile.launch(contentIntent)
        return true
    }
}
