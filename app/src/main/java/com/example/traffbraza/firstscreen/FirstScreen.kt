package com.example.traffbraza.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.traffbraza.NavRoute

@Composable
fun FirstScreen(
    navigation: NavController,
    isAllowedToOpenWebView: MutableState<Boolean>,
    urlParams: MutableState<String>,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.White,
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                if (isAllowedToOpenWebView.value) {
                    Log.d("TEST", "url params = ${urlParams.value}")
                    navigation.navigate(NavRoute.WebViewScreen.withArgs(urlParams.value))
                }
            }) {
                Text(text = "WebView")
            }
            Button(onClick = { navigation.navigate(NavRoute.GameScreen.path) }) {
                Text("Game")
            }
        }
    }
}
