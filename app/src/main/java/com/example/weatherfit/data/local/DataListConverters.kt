package com.example.weatherfit.data.local

import androidx.room.TypeConverter
import com.example.weatherfit.model.Data
import com.google.gson.Gson

class DataListConverters {
    @TypeConverter
    fun listToJson(value: List<Data>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Data>? {
        return Gson().fromJson(value, Array<Data>::class.java)?.toList()
    }
}