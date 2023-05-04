package com.example.traffbraza.webview.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val LAST_URL_KEY = "LAST_URL_KEY"
private const val defaultUrl = "https://google.com"

@HiltViewModel
class WebViewVM @Inject constructor(private val sharedPrefs: SharedPreferences) : ViewModel() {

    val lastUrl: String
        get() = sharedPrefs.getString(LAST_URL_KEY, defaultUrl) ?: defaultUrl

    fun saveLastUrl(url: String?) = sharedPrefs.edit().putString(LAST_URL_KEY, url).apply()
}
