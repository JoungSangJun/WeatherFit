package com.example.weatherfit.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherfit.WeatherFitApplication
import com.example.weatherfit.data.local.CurrentTime
import com.example.weatherfit.data.local.UserProfileRepository
import com.example.weatherfit.data.local.WeatherData
import com.example.weatherfit.data.local.WeatherDataDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
 data store에서 처음 값 가져오면 빈 값이라 nullpoint 에러남
 */
class HomeViewModel(
    private val weatherDataDao: WeatherDataDao,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _test: MutableStateFlow<String> = MutableStateFlow("")
    val test: StateFlow<String> = _test

    init {
        viewModelScope.launch {
            userProfileRepository.userSelectedArea.collect {
                _test.value = it
            }
        }
    }

    // 사용자가 선택한 지역의 기상 정보 가져오기
    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    val homeUiState: Flow<HomeUiState> = test.flatMapLatest {
        if (it.isEmpty()) {
            weatherDataDao.getSelectedTownNameWeather("").map {
                HomeUiState()
            }
        } else {
            weatherDataDao.getSelectedTownNameWeather(test.value).map {
                HomeUiState(
                    it.townName,
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
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeatherFitApplication)
                HomeViewModel(
                    weatherDataDao = application.weatherDatabase.weatherDataDao(),
                    userProfileRepository = application.userPreferencesRepository
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