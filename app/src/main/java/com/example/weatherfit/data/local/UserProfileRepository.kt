package com.example.weatherfit.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weatherfit.ui.item.UserProfileUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserProfileRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_AGE = stringPreferencesKey("user_age")
        val USER_SEX = stringPreferencesKey("user_sex")
        val USER_PURPOSE = stringPreferencesKey("user_purpose")
        val USER_BODY_TYPE = stringPreferencesKey("user_body_type")
        val USER_PREFERRED_STYLE = stringPreferencesKey("user_preferred_style")
        const val TAG = "UserProfileRepo"
    }

    suspend fun saveUserProfilePreference(userProfile: UserProfileUiState) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userProfile.name
            preferences[USER_AGE] = userProfile.age
            preferences[USER_SEX] = userProfile.sex
            preferences[USER_PURPOSE] = userProfile.purpose
            preferences[USER_BODY_TYPE] = userProfile.bodyType
            preferences[USER_PREFERRED_STYLE] = userProfile.preferredStyle
        }
    }

    val userProfile: Flow<UserProfileUiState> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        val name = preferences[USER_NAME] ?: ""
        val age = preferences[USER_AGE] ?: ""
        val sex = preferences[USER_SEX] ?: ""
        val purpose = preferences[USER_PURPOSE] ?: ""
        val bodyType = preferences[USER_BODY_TYPE] ?: ""
        val preferredStyle = preferences[USER_PREFERRED_STYLE] ?: ""

        UserProfileUiState(name, age, sex, purpose, bodyType, preferredStyle)
    }

}