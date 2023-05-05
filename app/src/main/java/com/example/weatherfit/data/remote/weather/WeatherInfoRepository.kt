package com.example.weatherfit.data.remote.weather

import com.example.weatherfit.model.WeatherData
import com.example.weatherfit.network.WeatherApiService

interface WeatherInfoRepository {
    suspend fun getWeather(
        page_no: Int,
        num_of_rows: Int,
        data_type: String,
        base_date: Int,
        base_time: Int,
        nx: String,
        ny: String
    ): WeatherData
}

class NetworkWeatherInfoRepository(private val weatherApiService : WeatherApiService) : WeatherInfoRepository {
    override suspend fun getWeather(
        page_no: Int,
        num_of_rows: Int,
        data_type: String,
        base_date: Int,
        base_time: Int,
        nx: String,
        ny: String
    ): WeatherData = weatherApiService.getWeather(page_no,num_of_rows,data_type,base_date,base_time,nx,ny)
}