package com.example.weatherfit

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherfit.data.local.UserProfileRepository

private const val USER_PROFILE_PREFERENCE_NAME = "user_profile_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PROFILE_PREFERENCE_NAME
)


class WeatherFitApplication: Application() {
    lateinit var userPreferencesRepository: UserProfileRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserProfileRepository(dataStore)
    }
}