package com.example.weatherfit.ui.areaSetting

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherfit.WeatherFitTopAppBar
import com.example.weatherfit.data.local.CityCategory.City


@Composable
fun AreaAddScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            WeatherFitTopAppBar(
                title = "지역 추가",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) {

        Column(modifier = Modifier.padding(it)) {
            DropDownMenu("도 / 특별시 / 광역시", City)
            DropDownMenu("시 / 구 / 군", City)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(),
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
fun DropDownMenu(title: String, item: Array<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(item[0]) }
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
                                    selectedText = item
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