package com.example.weatherfit.ui.areaSetting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.stringResource
import com.example.weatherfit.R
import com.example.weatherfit.WeatherFitTopAppBar
import com.example.weatherfit.data.local.WeatherData
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecastScreen(
    onNavigateToAreaAdd: () -> Unit,
    weatherForecastViewModel: WeatherForecastViewModel = viewModel(factory = WeatherForecastViewModel.Factory),
) {

    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH")
    val currentTime = current.format(formatter) + "00"
    val year = current.year.toString()
    val month = "%02d".format(current.monthValue)
    val day = "%02d".format(current.dayOfMonth)
    val today = year + month + day
    val weatherData by weatherForecastViewModel.getAllCityWeather()
        .collectAsState(emptyList())

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
        LazyColumn(modifier = Modifier.padding(it)) {
            items(weatherData) {
                WeatherDataListItem(
                    weatherData = it,
                    currentTime = currentTime,
                    today = today
                )
            }
        }
    }
}

@Composable
fun WeatherDataListItem(weatherData: WeatherData, currentTime: String, today: String) {
    val currentTmp = weatherData.weatherData.filter {
        it.category == "TMP" && it.fcstTime == currentTime
    }.map {
        it.fcstValue
    }[0]
    val tmpMax = weatherData.weatherData.filter {
        it.category == "TMX" && it.fcstDate == today
    }.map {
        it.fcstValue
    }[0]
    val tmpMin = weatherData.weatherData.filter {
        it.category == "TMN" && it.fcstDate == today
    }.map {
        it.fcstValue
    }[0]
    Card(
        modifier = Modifier
            .padding(10.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            Row() {
                Text(fontSize = 23.sp, text = weatherData.townName)
                Spacer(Modifier.weight(1f))
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    text = currentTmp + "C°"
                )
            }
            Spacer(Modifier.height(5.dp))
            Row() {
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_drop_up_24),
                    modifier = Modifier.size(30.dp),
                    contentDescription = ""
                )
                Text(tmpMax + "C°")
                Image(
                    painter = painterResource(id = R.drawable.outline_arrow_drop_down_24),
                    modifier = Modifier.size(30.dp),
                    contentDescription = ""
                )
                Text(tmpMin + "C°")
            }
        }
    }
}