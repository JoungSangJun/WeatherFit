package com.example.weatherfit.data.local

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.weatherfit.model.Data

@Entity(
    tableName = "Weather",
    indices = [Index(value = ["town_name"], unique = true)]
)
data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull
    @ColumnInfo(name = "town_name")
    val townName: String = "",
    @NonNull
    @ColumnInfo(name = "weather_data")
    val weatherData: List<Data> = emptyList()
)