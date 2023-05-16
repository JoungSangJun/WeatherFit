package com.example.weatherfit.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherfit.WeatherFitApplication
import com.example.weatherfit.data.local.CurrentTime
import com.example.weatherfit.data.local.WeatherDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

/*
 home screen에서 사용할 데이터 uiState로 만들기
 */
class HomeViewModel(private val weatherDataDao: WeatherDataDao) : ViewModel() {

    // 사용자가 선택한 지역의 기상 정보 가져오기
    @RequiresApi(Build.VERSION_CODES.O)
    val homeUiState: Flow<HomeUiState> = weatherDataDao.getSelectedTownNameWeather("강릉시").map {
        HomeUiState(it.townName,
            tmp = it.weatherData.filter { it.category == "TMP" && it.fcstTime == CurrentTime.currentTime }
                .map { it.fcstValue }.firstOrNull() ?: "0",
            tmpMax = it.weatherData.filter {
                it.category == "TMX" && it.fcstDate == CurrentTime.today
            }.map {
                it.fcstValue
            }.firstOrNull() ?: "0",
            tmpMin = it.weatherData.filter {
                it.category == "TMN" && it.fcstDate == CurrentTime.today
            }.map {
                it.fcstValue
            }.firstOrNull() ?: "0",
            windSpeed = it.weatherData.filter {
                it.category == "WSD" && it.fcstTime == CurrentTime.currentTime
            }.map {
                it.fcstValue
            }.firstOrNull() ?: "0",
            rainPercent = it.weatherData.filter {
                it.category == "POP" && it.fcstTime == CurrentTime.currentTime
            }.map {
                it.fcstValue
            }.firstOrNull() ?: "0",
            humidity = it.weatherData.filter {
                it.category == "REH" && it.fcstTime == CurrentTime.currentTime
            }.map {
                it.fcstValue
            }.firstOrNull() ?: "0"
        )
    }

    fun getSelectedTownNameWeather(townName: String) =
        weatherDataDao.getSelectedTownNameWeather(townName)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeatherFitApplication)
                HomeViewModel(
                    weatherDataDao = application.weatherDatabase.weatherDataDao()
                )
            }
        }
    }
}

data class HomeUiState(
    val townName: String = "",
    val tmp: String = "",
    val tmpMax: String = "",
    val tmpMin: String = "",
    val windSpeed: String = "",
    val rainPercent: String = "",
    val humidity: String = ""
)