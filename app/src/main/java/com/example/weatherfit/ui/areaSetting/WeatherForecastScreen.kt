package com.example.weatherfit.ui.areaSetting

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.stringResource
import com.example.weatherfit.R
import com.example.weatherfit.WeatherFitTopAppBar


@Composable
fun WeatherForecastScreen(onNavigateToAreaAdd: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAreaAdd,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_area),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        topBar = {
            WeatherFitTopAppBar(
                title = "날씨",
                canNavigateBack = false,
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}