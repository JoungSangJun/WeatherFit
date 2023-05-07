package com.example.weatherfit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherData: WeatherData)

}