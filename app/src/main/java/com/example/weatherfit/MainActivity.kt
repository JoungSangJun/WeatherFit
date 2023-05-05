package com.example.weatherfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.weatherfit.ui.navigation.WeatherFitNavHost
import com.example.weatherfit.ui.theme.WeatherFitTheme

// 기상청에서 값 받아올 때 인터넷 연결 확인, 값 받아오는 중 추가하기
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherFitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WeatherFitNavHost(navController = rememberNavController(),this)
                }
            }
        }
    }
}
