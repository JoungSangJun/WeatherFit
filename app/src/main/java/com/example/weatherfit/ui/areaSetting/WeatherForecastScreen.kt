package com.example.weatherfit.ui.areaSetting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherfit.data.remote.weather.WeatherDataSource
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
        LazyColumn(Modifier.padding(it)) {
            items(WeatherDataSource().loadAffirmations()) { weatherData ->
                Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
                    Row(
                        modifier = Modifier
                            .background(Color.LightGray)
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(weatherData.location)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(weatherData.Temperatures.toString() + "C")
                    }
                }
            }
        }
    }
}