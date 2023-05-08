package com.example.weatherfit.data.remote.weather

import com.example.weatherfit.model.WeatherDataUiState
import com.example.weatherfit.network.WeatherApiService

/**
 * 기상청 API 통신 class
 */
interface WeatherInfoRepository {
    suspend fun getWeather(
        page_no: Int,
        num_of_rows: Int,
        data_type: String,
        base_date: Int,
        base_time: Int,
        nx: String,
        ny: String
    ): WeatherDataUiState
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
    ): WeatherDataUiState = weatherApiService.getWeather(page_no,num_of_rows,data_type,base_date,base_time,nx,ny)
}