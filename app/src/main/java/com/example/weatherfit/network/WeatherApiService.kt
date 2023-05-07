package com.example.weatherfit.network

import com.example.weatherfit.BuildConfig
import com.example.weatherfit.model.WeatherDataUiState
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("getVilageFcst?serviceKey=${BuildConfig.WEATHER_API_KEY}")
    suspend fun getWeather(
        @Query("pageNo") page_no: Int,
        @Query("numOfRows") num_of_rows: Int,
        @Query("dataType") data_type: String,
        @Query("base_date") base_date: Int,
        @Query("base_time") base_time: Int,
        @Query("nx") nx: String,
        @Query("ny") ny: String
    ): WeatherDataUiState
}