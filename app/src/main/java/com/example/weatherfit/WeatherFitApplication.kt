package com.example.weatherfit

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherfit.data.local.UserProfileRepository
import com.example.weatherfit.data.local.WeatherDatabase
import com.example.weatherfit.data.remote.workerData.ClothAppContainer
import com.example.weatherfit.data.remote.workerData.ClothRecommendAppContainer
import com.example.weatherfit.data.remote.weather.AppContainer
import com.example.weatherfit.data.remote.weather.WeatherAppContainer

private const val USER_PROFILE_PREFERENCE_NAME = "user_profile_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PROFILE_PREFERENCE_NAME
)


class WeatherFitApplication : Application() {
    lateinit var userPreferencesRepository: UserProfileRepository
    lateinit var container: AppContainer
    lateinit var clothContainer: ClothRecommendAppContainer
    val weatherDatabase: WeatherDatabase by lazy { WeatherDatabase.getDatabase(this) }
    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserProfileRepository(dataStore)
        clothContainer = ClothAppContainer(this)
        container = WeatherAppContainer()
    }
}