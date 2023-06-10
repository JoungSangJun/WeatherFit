package com.example.weatherfit.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherfit.R
import com.example.weatherfit.ui.profile.UserProfileUiState

// 날씨에 따라 해, 비, 구름 등 이미지 변경되어야함

val gradient = Brush.verticalGradient(
    colors = listOf(Color.DarkGray, Color.White),
    startY = 0.0f,
    endY = 3000.0f
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)) {
    val homeUiState by homeViewModel.homeUiState.collectAsState(initial = HomeUiState())
    val clothRecommend by homeViewModel.clothRecommend.collectAsState()
    val userProfile = homeViewModel.userProfile.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TempCard(homeUiState.townName, homeUiState.tmp, homeUiState.tmpMax, homeUiState.tmpMin)
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                WeatherInformationCard(infoKind = "풍속", value = homeUiState.windSpeed)
                Spacer(modifier = Modifier.weight(1f))
                WeatherInformationCard(infoKind = "강수확률", value = homeUiState.rainPercent)
                Spacer(modifier = Modifier.weight(1f))
                WeatherInformationCard(infoKind = "습도", value = homeUiState.humidity)
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
            ClothRecommendCard(
                clothRecommend,
                homeUiState,
                userProfile
            ) { userProfile, weatherData ->
                homeViewModel.makeQuestionForChatGpt(userProfile, weatherData)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ClothRecommendCard(
    clothRecommend: String,
    homeUiState: HomeUiState,
    userProfile: UserProfileUiState,
    makeQuestionForChatGpt: (UserProfileUiState, HomeUiState) -> Unit
) {
    Card(
        modifier = Modifier
            .width(350.dp)
            .height(150.dp)
            .alpha(0.8f),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(40.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 10.dp)
        ) {
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { makeQuestionForChatGpt(userProfile, homeUiState) },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_refresh_24),
                        contentDescription = "Button Icon",
                        tint = Color.Black
                    )
                }
            }
            Text(
                clothRecommend.trim(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp, end = 10.dp)
            )
        }
    }
}

@Composable
fun WeatherInformationCard(infoKind: String = "", value: String = "") {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .alpha(0.8f),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = infoKind, fontWeight = FontWeight.Bold)
            Text(
                text = when (infoKind) {
                    "풍속" -> "$value m/s"
                    else -> "$value %"
                }, fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TempCard(townName: String = "", tmp: String = "", tmpMax: String = "", tmpMin: String = "") {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(350.dp)
            .alpha(0.8f),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = townName,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 30.dp)
            )
            Image(
                painter = painterResource(R.drawable.baseline_wb_sunny_24),
                contentDescription = "sun",
                modifier = Modifier
                    .size(130.dp)
            )
            Text(
                text = tmp + "C°",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(20.dp)
            )
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_drop_up_24),
                    modifier = Modifier.size(30.dp),
                    contentDescription = ""
                )
                Text(text = tmpMax + "C°")
                Image(
                    painter = painterResource(id = R.drawable.outline_arrow_drop_down_24),
                    modifier = Modifier.size(30.dp),
                    contentDescription = ""
                )
                Text(text = tmpMin + "C°")
            }
        }
    }
}