package com.example.weatherfit.ui.areaSetting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherfit.WeatherFitTopAppBar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AreaAddScreen(
    onNavigateUp: () -> Unit,
    areaAddViewModel: AreaAddViewModel = viewModel(factory = AreaAddViewModel.Factory)
) {
    val cityName = areaAddViewModel.cityList
    val townName = areaAddViewModel.townList
    val selectedCity = areaAddViewModel.selectedCity
    val selectedTown = areaAddViewModel.selectedTown

    Scaffold(
        topBar = {
            WeatherFitTopAppBar(
                title = "지역 추가",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { it ->
        Column(modifier = Modifier.padding(it)) {
            DropDownMenu(
                title = "도 / 특별시 / 광역시",
                item = cityName,
                selectedText = selectedCity,
                onValueChange = { areaAddViewModel.selectedCityChange(it) })
            DropDownMenu(
                title = "시 / 구 / 군",
                item = townName.map { it.townName }.toTypedArray(),
                selectedText = selectedTown,
                onValueChange = { areaAddViewModel.selectedTownChange(it) })
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { areaAddViewModel.getWeatherInfo()}, modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                )
            ) {
                Text("추가하기")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(
    title: String,
    item: Array<String>,
    selectedText: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, top = 30.dp),
        elevation = 0.dp
    ) {
        Column() {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = selectedText,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(4.dp)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        item.forEach { item ->
                            DropdownMenuItem(
                                content = { Text(text = item) },
                                onClick = {
                                    onValueChange(item)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}