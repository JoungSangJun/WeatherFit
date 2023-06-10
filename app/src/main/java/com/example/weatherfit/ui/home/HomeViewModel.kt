package com.example.weatherfit.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherfit.WeatherFitApplication
import com.example.weatherfit.data.local.CurrentTime
import com.example.weatherfit.data.local.UserProfileRepository
import com.example.weatherfit.data.local.WeatherDataDao
import com.example.weatherfit.data.remote.chatGpt.ChatGptRepository
import com.example.weatherfit.ui.profile.UserProfileUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherDataDao: WeatherDataDao,
    private val userProfileRepository: UserProfileRepository,
    private val chatGptRepository: ChatGptRepository
) : ViewModel() {

    private val userSelectedArea: MutableStateFlow<String> = MutableStateFlow("")
    val userProfile: StateFlow<UserProfileUiState> = userProfileRepository.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserProfileUiState()
    )

    private val _clothRecommend: MutableStateFlow<String> = MutableStateFlow("")
    val clothRecommend: StateFlow<String> = _clothRecommend

    init {
        viewModelScope.launch {
            userProfileRepository.userSelectedArea.collect {
                userSelectedArea.value = it
            }
        }
    }

    private fun getRecommendCloth(question: String) {
        viewModelScope.launch {
            _clothRecommend.value = chatGptRepository.getRecommendCloth(question)
        }
    }

    // 사용자가 선택한 지역의 기상 정보 가져오기
    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    val homeUiState: Flow<HomeUiState> = userSelectedArea.flatMapLatest {
        if (it.isEmpty()) {
            weatherDataDao.getSelectedTownNameWeather("").map {
                HomeUiState()
            }
        } else {
            weatherDataDao.getSelectedTownNameWeather(userSelectedArea.value).map {
                HomeUiState(
                    it.townName,
                    tmp = it.weatherData.filter { it.category == "TMP" && it.fcstTime == CurrentTime.currentTime }
                        .map { it.fcstValue }.firstOrNull() ?: "0",
                    tmpMax = it.weatherData.filter {
                        it.category == "TMX" && it.fcstDate == CurrentTime.today
                    }.map {
                        it.fcstValue
                    }.firstOrNull() ?: "0",
                    tmpMin = it.weatherData.filter {
                        it.category == "TMN" && it.fcstDate == CurrentTime.today
                    }.map {
                        it.fcstValue
                    }.firstOrNull() ?: "0",
                    windSpeed = it.weatherData.filter {
                        it.category == "WSD" && it.fcstTime == CurrentTime.currentTime
                    }.map {
                        it.fcstValue
                    }.firstOrNull() ?: "0",
                    rainPercent = it.weatherData.filter {
                        it.category == "POP" && it.fcstTime == CurrentTime.currentTime
                    }.map {
                        it.fcstValue
                    }.firstOrNull() ?: "0",
                    humidity = it.weatherData.filter {
                        it.category == "REH" && it.fcstTime == CurrentTime.currentTime
                    }.map {
                        it.fcstValue
                    }.firstOrNull() ?: "0",
                )
            }
        }
    }

    // chat GPT에게 물어볼 질문 생성
    @RequiresApi(Build.VERSION_CODES.O)
    fun makeQuestionForChatGpt(userProfile: UserProfileUiState, weatherData: HomeUiState) {
        val question: String =
            userProfile.age + "살" + userProfile.sex + userProfile.purpose + "목적" +
                    userProfile.bodyType + "체형" + userProfile.preferredStyle + "스타일" +
                    "최고기온" + weatherData.tmpMax + "최저기온" + weatherData.tmpMin +
                    weatherData.tmp + "도에서 옷 색상과 함께 상의 하의 신발 1개씩 추천해"
        getRecommendCloth(question)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeatherFitApplication)
                HomeViewModel(
                    weatherDataDao = application.weatherDatabase.weatherDataDao(),
                    userProfileRepository = application.userPreferencesRepository,
                    chatGptRepository = application.chatGptRepository
                )
            }
        }
    }
}

data class HomeUiState(
    val townName: String = "",
    val tmp: String = "",
    val tmpMax: String = "",
    val tmpMin: String = "",
    val windSpeed: String = "",
    val rainPercent: String = "",
    val humidity: String = "",
)