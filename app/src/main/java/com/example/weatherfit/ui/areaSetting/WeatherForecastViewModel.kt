package com.example.weatherfit.ui.areaSetting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherfit.WeatherFitApplication
import com.example.weatherfit.data.local.WeatherData
import com.example.weatherfit.data.local.WeatherDataDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class WeatherForecastViewModel(private val weatherDataDao: WeatherDataDao) : ViewModel() {

    fun getAllCityWeather(): Flow<List<WeatherData>> = weatherDataDao.getAllCityWeather()

    fun deleteSelectedData(townName: String) = viewModelScope.launch {
        weatherDataDao.deleteSelectedData(townName)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeatherFitApplication)
                WeatherForecastViewModel(
                    weatherDataDao = application.weatherDatabase.weatherDataDao()
                )
            }
        }
    }
}