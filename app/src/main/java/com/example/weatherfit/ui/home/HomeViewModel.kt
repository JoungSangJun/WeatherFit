package com.example.weatherfit.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherfit.WeatherFitApplication
import com.example.weatherfit.data.local.WeatherDataDao
import com.example.weatherfit.ui.areaSetting.WeatherForecastViewModel

class HomeViewModel(private val weatherDataDao: WeatherDataDao) : ViewModel() {

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