package com.example.weatherfit.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.example.weatherfit.R
import com.example.weatherfit.ui.areaSetting.AreaAddScreen
import com.example.weatherfit.ui.areaSetting.WeatherForecastScreen
import com.example.weatherfit.ui.home.HomeScreen
import com.example.weatherfit.ui.profile.ProfileSettingScreen

sealed class BottomNavItem(val route: String, val icon: Int) {
    object Home : BottomNavItem("home", R.drawable.baseline_home_24)
    object AreaSetting : BottomNavItem("areaSetting", R.drawable.baseline_add_location_alt_24)
    object ProfileSetting : BottomNavItem("profileSetting", R.drawable.baseline_account_circle_24)
}

sealed class AreaSettingNavItem(val route: String) {
    object WeatherForecast : AreaSettingNavItem("weatherForecast")
    object AreaAdd : AreaSettingNavItem("areaAdd")
}

@Composable
fun WeatherFitNavHost(
    navController: NavHostController,
    context: Context,
    modifier: Modifier = Modifier,
) {
    var showBottomBar by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        AreaSettingNavItem.AreaAdd.route -> false
        else -> true
    }
    Scaffold(
        bottomBar = { if (showBottomBar) BottomNavigation(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = BottomNavItem.Home.route) {
                HomeScreen()
            }
            areaSettingGraph(navController)
            composable(route = BottomNavItem.ProfileSetting.route) {
                ProfileSettingScreen(context = context)
            }
        }
    }
}

fun NavGraphBuilder.areaSettingGraph(navController: NavController) {
    navigation(
        startDestination = AreaSettingNavItem.WeatherForecast.route,
        route = BottomNavItem.AreaSetting.route
    ) {
        composable(AreaSettingNavItem.WeatherForecast.route) {
            WeatherForecastScreen(onNavigateToAreaAdd = { navController.navigate(AreaSettingNavItem.AreaAdd.route) })
        }
        composable(AreaSettingNavItem.AreaAdd.route) {
            AreaAddScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    // 바텀 네비게이션 item들의 정보
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.AreaSetting,
        BottomNavItem.ProfileSetting,
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.route,
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Gray,
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            // Home 화면만 Stack에 저장
                            popUpTo(it) { saveState = true }
                        }
                        // 화면 인스턴스 1개 생성
                        launchSingleTop = true
                        // 바텀네비게이션 버튼 재클릭시 이전 값 저장
                        restoreState = true
                    }
                }
            )
        }
    }
}