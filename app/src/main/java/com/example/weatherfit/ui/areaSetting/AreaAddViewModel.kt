package com.example.weatherfit.ui.areaSetting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherfit.WeatherFitApplication
import com.example.weatherfit.data.local.CityCategory
import com.example.weatherfit.data.local.TownInfo
import com.example.weatherfit.data.remote.weather.WeatherInfoRepository
import kotlinx.coroutines.launch

class AreaAddViewModel(private val weatherInfoRepository: WeatherInfoRepository) : ViewModel() {
    val cityList = CityCategory.City
    var townList by mutableStateOf(CityCategory.TownGangwon)
    var selectedCity by mutableStateOf(cityList[0])
    var selectedTown by mutableStateOf(townList[0].townName)
    var selectedTownNx by mutableStateOf(townList[0].nx)
    var selectedTownNy by mutableStateOf(townList[0].ny)


    fun selectedCityChange(changedCityName: String) {
        selectedCity = changedCityName
        // 도 / 특별시 / 광역시 이름 변경시 선택 가능한 시 / 구 / 군 이름 변경 되어야함
        selectedTown = ""
        townList = updateTownOptions(changedCityName)
    }

    private fun updateTownOptions(cityName: String): Array<TownInfo> {
        return when (cityName) {
            "강원도" -> CityCategory.TownGangwon
            "경기도" -> CityCategory.TownGyeonggi
            "경상남도" -> CityCategory.TownGyeongSangNamDo
            "경상북도" -> CityCategory.TownGyeongSangBukDo
            "광주광역시" -> CityCategory.TownGwangJu
            "대구광역시" -> CityCategory.TownDaeGu
            "대전광역시" -> CityCategory.TownDaeJeon
            "부산광역시" -> CityCategory.TownBusan
            "서울특별시" -> CityCategory.TownSeoul
            "울산광역시" -> CityCategory.TownUlsan
            "세종특별자치시" -> CityCategory.TownSejong
            "인천광역시" -> CityCategory.TownIncheon
            "전라남도" -> CityCategory.TownJeolLanamDo
            else -> CityCategory.TownChungCheongBukDo
        }
    }

    fun selectedTownChange(changedTownName: String) {
        selectedTown = changedTownName
        selectedTownNx = townList.find { it.townName == changedTownName }!!.nx
        selectedTownNy = townList.find { it.townName == changedTownName }!!.ny
    }

    fun getWeatherInfo(
        page_no: Int = 1,
        num_of_rows: Int = 1000,
        data_type: String = "Json",
        base_date: Int = 20230503,
        base_time: Int = 1700,
        nx: String = selectedTownNx,
        ny: String = selectedTownNy
    ) {
        viewModelScope.launch {
            val result = weatherInfoRepository.getWeather(
                page_no,
                num_of_rows,
                data_type,
                base_date,
                base_time,
                nx,
                ny
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[APPLICATION_KEY] as WeatherFitApplication)
                val weatherRepository = application.container.weatherInfoRepository
                AreaAddViewModel(weatherInfoRepository = weatherRepository)
            }
        }
    }


}