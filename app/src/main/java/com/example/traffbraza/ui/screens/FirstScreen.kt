package com.example.traffbraza.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.traffbraza.ui.Routes

@Composable
fun FirstScreen(
    navigation: NavController,
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
            Button(onClick = { navigation.navigate(Routes.WEB_VIEW_SCREEN) }) {
                Text(text = "WebView")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Game")
            }
        }
    }
}
