package com.example.weatherfit.data.local

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherfit.model.Data

@Entity(
    tableName = "Weather"
)
data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NonNull
    @ColumnInfo(name = "town_name")
    val townName: String,
    @NonNull
    @ColumnInfo(name = "weather_data")
    val weatherData: List<Data>
)