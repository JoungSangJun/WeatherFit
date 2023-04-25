package com.example.weatherfit.ui.areaSetting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.weatherfit.WeatherFitTopAppBar

@Composable
fun AreaAddScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            WeatherFitTopAppBar(
                title = "지역 추가",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) { Text("AreaAddScreen") }

    }
}