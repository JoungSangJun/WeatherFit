package com.example.weatherfit.data.remote.weather

import com.example.weatherfit.network.WeatherApiService
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType


interface AppContainer {
    val weatherInfoRepository: WeatherInfoRepository
}

class WeatherAppContainer : AppContainer {
    private val BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    override val weatherInfoRepository: WeatherInfoRepository by lazy {
        NetworkWeatherInfoRepository(retrofitService)
    }
}