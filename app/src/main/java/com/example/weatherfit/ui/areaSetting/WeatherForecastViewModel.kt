package com.example.weatherfit.ui.areaSetting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherfit.WeatherFitApplication
import com.example.weatherfit.data.local.UserProfileRepository
import com.example.weatherfit.data.local.WeatherData
import com.example.weatherfit.data.local.WeatherDataDao
import com.example.weatherfit.ui.profile.UserProfileUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class WeatherForecastViewModel(
    private val weatherDataDao: WeatherDataDao,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    // 유저가 선택한 지역 dataStroe preferences 에서 가져옴
    val uiState: StateFlow<String> = userProfileRepository.userSelectedArea.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
    )

    fun getAllCityWeather(): Flow<List<WeatherData>> = weatherDataDao.getAllCityWeather()

    fun deleteSelectedData(townName: String) = viewModelScope.launch {
        weatherDataDao.deleteSelectedData(townName)
        userProfileRepository.userSelectedArea.collect {
            if (townName == it) {
                userProfileRepository.saveUserSelectedArea("")
            }
        }
    }

    fun saveUserSelectedArea(userSelectedArea: String) {
        viewModelScope.launch {
            userProfileRepository.saveUserSelectedArea(userSelectedArea)
        }
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeatherFitApplication)
                WeatherForecastViewModel(
                    weatherDataDao = application.weatherDatabase.weatherDataDao(),
                    userProfileRepository = application.userPreferencesRepository
                )
            }
        }
    }
}