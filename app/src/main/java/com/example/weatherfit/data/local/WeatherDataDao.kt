package com.example.weatherfit.data.local

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherData: WeatherData)

    @Transaction
    suspend fun insertWithDupCheck(weatherData: WeatherData, context: Context) {
        val count = checkDuplication(weatherData.townName)
        val handler = Handler(Looper.getMainLooper())
        if (count > 0) {
            handler.postDelayed(Runnable {
                Toast.makeText(context, "선택하신 지역이 이미 존재합니다.", Toast.LENGTH_SHORT).show()
            }, 0)
            return
        }
        insert(weatherData)
    }

    @Query("SELECT * FROM Weather WHERE town_name = :townName")
    fun getSelectedTownNameWeather(townName: String): Flow<WeatherData>

    @Query("SELECT COUNT(*) FROM Weather WHERE town_name = :townName")
    suspend fun checkDuplication(townName: String): Int

    @Query(
        """
        SELECT * FROM weather 
        """
    )
    fun getAllCityWeather(): Flow<List<WeatherData>>

    @Query("Delete From Weather WHERE town_name = :townName")
    suspend fun deleteSelectedData(townName: String)
}