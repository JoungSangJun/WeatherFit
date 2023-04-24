package com.example.weatherfit.data.remote

import com.example.weatherfit.model.WeatherData

class WeatherDataSource {
    fun loadAffirmations(): List<WeatherData> {
        return listOf<WeatherData>(
            WeatherData("인천", 17),
            WeatherData("천안", 18),
        )
    }
}