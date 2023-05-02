package com.example.weatherfit.ui.areaSetting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.weatherfit.data.local.CityCategory
import com.example.weatherfit.data.local.TownInfo

class AreaAddViewModel : ViewModel() {
    val cityList = CityCategory.City
    var townList by mutableStateOf(CityCategory.TownGangwon)
    var selectedCity by mutableStateOf(cityList[0])
    var selectedTown by mutableStateOf(townList[0].townName)

    fun selectedCityChange(changedCityName: String) {
        selectedCity = changedCityName
        // 도 / 특별시 / 광역시 이름 변경시 선택 가능한 시 / 구 / 군 이름 변경 되어야함
        selectedTown = ""
        townList = updateTownOptions(changedCityName)
    }

    fun selectedTownChange(changedTownName: String) {
        selectedTown = changedTownName
    }

    private fun updateTownOptions(cityName: String): Array<TownInfo> {
        return when (cityName) {
            "강원도" -> CityCategory.TownGangwon
            else -> CityCategory.TownGyeonggi
        }
    }
}