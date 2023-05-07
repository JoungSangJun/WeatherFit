package com.example.weatherfit.ui.areaSetting

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.weatherfit.data.local.WeatherData
import com.example.weatherfit.data.local.WeatherDataDao
import com.example.weatherfit.data.remote.weather.WeatherInfoRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class AreaAddViewModel(
    private val weatherInfoRepository: WeatherInfoRepository,
    private val weatherDataDao: WeatherDataDao,
) : ViewModel() {

    private val yesterday: Int
    val cityList = CityCategory.City
    var townList by mutableStateOf(CityCategory.TownGangwon)
    var selectedCity by mutableStateOf(cityList[0])
    var selectedTown by mutableStateOf(townList[0].townName)
    var selectedTownNx by mutableStateOf(townList[0].nx)
    var selectedTownNy by mutableStateOf(townList[0].ny)


    init {
        val nowInKorea = LocalDate.now(ZoneId.of("Asia/Seoul"))
        val year = nowInKorea.year.toString()
        val month = "%02d".format(nowInKorea.monthValue)
        val day = "%02d".format(nowInKorea.dayOfMonth)
        // 하루 전의 날짜 받기
        yesterday = (year + month + day).toInt() - 1
    }


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

    /**
     * 다음 날 하루 기온 보기 위해선 전날 23시에 예보를 봐야함
     *
     * page_no : num_of_rows 기준으로 페이지 번호. ex) num_of_rows = 1, page_no = 2 이면 두 번째 데이터 한 개 출력
     * num_of_rows : 한 페이지에서 결과로 받아오는 날씨정보의 수 1시간에 12개 하루에 290, 12 * 12 = 288 and 최고기온, 최저기온
     * base_date : 관측한 날짜
     * base_time : 관측한 시간
     **/
    fun getWeatherInfo(
        page_no: Int = 1,
        num_of_rows: Int = 580,
        data_type: String = "Json",
        base_date: Int = yesterday,
        base_time: Int = 2300,
        nx: String = selectedTownNx,
        ny: String = selectedTownNy,
        context: Context
    ) {
        viewModelScope.launch {
            val weatherUiState = weatherInfoRepository.getWeather(
                page_no,
                num_of_rows,
                data_type,
                base_date,
                base_time,
                nx,
                ny
            )

            // 같은 값 저장 막기
            weatherDataDao.insertWithDupCheck(
                WeatherData(
                    id = 0,
                    townName = selectedTown,
                    weatherData = weatherUiState.response.body.items.item
                ),
                context = context
            )
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[APPLICATION_KEY] as WeatherFitApplication)
                val weatherRepository = application.container.weatherInfoRepository
                AreaAddViewModel(
                    weatherInfoRepository = weatherRepository,
                    weatherDataDao = application.weatherDatabase.weatherDataDao()
                )
            }
        }
    }
}