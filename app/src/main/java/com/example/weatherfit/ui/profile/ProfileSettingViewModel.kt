package com.example.weatherfit.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherfit.WeatherFitApplication
import com.example.weatherfit.data.local.UserProfileRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileSettingViewModel(private val userProfileRepository: UserProfileRepository) :
    ViewModel() {

    val uiState: StateFlow<UserProfileUiState> = userProfileRepository.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserProfileUiState()
    )

    fun saveUserProfilePreference(userProfile: UserProfileUiState) {
        viewModelScope.launch {
            userProfileRepository.saveUserProfilePreference(userProfile)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[APPLICATION_KEY] as WeatherFitApplication)
                ProfileSettingViewModel(application.userPreferencesRepository)
            }
        }
    }
}

data class UserProfileUiState(
    val name: String = "",
    val age: String = "",
    val sex: String = "",
    val purpose: String = "",
    val bodyType: String = "",
    val preferredStyle: String = ""
)
