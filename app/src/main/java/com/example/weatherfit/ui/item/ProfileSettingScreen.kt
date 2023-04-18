package com.example.weatherfit.ui.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherfit.R

// 목적 체형 선호하는 스타일 enum class로 묶기

@Composable
fun ProfileSettingScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileSettingViewModel = viewModel(
        factory = ProfileSettingViewModel.Factory
    ),
) {
    Column() {
        UserProfileScreen()
        UserClothStyleScreen()
    }
}

@Composable
fun UserProfileScreen() {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(vertical = 10.dp, horizontal = 25.dp)
        ) {
            Text("개인정보", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.baseline_portrait_24),
                modifier = Modifier.size(30.dp),
                contentDescription = ""
            )
        }
        Row(modifier = Modifier.padding(top = 15.dp)) {
            Text(
                text = "이름",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "나이",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "성별",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
        Row() {
            TextField(
                value = "장세종",
                onValueChange = { it },
                modifier = Modifier
                    .weight(1f),
                textStyle = TextStyle(textAlign = TextAlign.Center),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            TextField(
                value = "24",
                onValueChange = { it },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(textAlign = TextAlign.Center),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            SexDropDownMenu(modifier = Modifier.weight(1f))
        }
    }

}

@Composable
fun SexDropDownMenu(modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("남자") }
    val options = listOf("남자", "여자")

    Column(modifier = modifier) {
        Text(
            text = selectedOption,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    selectedOption = option
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun UserClothStyleScreen() {
    Column() {
        Text(
            "옷 추천 설정",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 25.dp),
            fontSize = 20.sp
        )
        Text(
            "목적",
            modifier = Modifier.padding(start = 30.dp, top = 10.dp),
            fontWeight = FontWeight.Bold
        )
        IconSelectorScreen("운동", "데이트", "비지니스", "결혼식")
        Text("체형", modifier = Modifier.padding(start = 30.dp), fontWeight = FontWeight.Bold)
        IconSelectorScreen(
            selectedValue1 = "삼각형",
            selectedValue2 = "역삼각형",
            selectedValue3 = "사각형",
            selectedValue4 = "타원형"
        )
        Text("선호하는 스타일", modifier = Modifier.padding(start = 30.dp), fontWeight = FontWeight.Bold)
        IconSelectorScreen(
            selectedValue1 = "클래식",
            selectedValue2 = "캐주얼",
            selectedValue3 = "스트릿",
            selectedValue4 = "빈티지"
        )
    }
}


/**
 * 목적, 체형, 선호하는 스타일의 IconToggleButton UI
 */
@Composable
fun IconSelectorScreen(
    selectedValue1: String,
    selectedValue2: String,
    selectedValue3: String,
    selectedValue4: String,
) {
    var selectedButton by remember { mutableStateOf(0) }

    val buttonOptions = listOf(selectedValue1, selectedValue2, selectedValue3, selectedValue4)

    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .selectableGroup()
    ) {
        buttonOptions.forEachIndexed { index, text ->
            Surface(
                Modifier
                    .weight(0.7f)
                    .height(80.dp)
                    .selectable(
                        selected = (selectedButton == index),
                        onClick = { selectedButton = index }
                    )
                    .padding(horizontal = 5.dp),
                shape = CircleShape,
                border = if (selectedButton == index) BorderStroke(
                    2.dp,
                    Color.Blue
                ) else BorderStroke(2.dp, Color.Black)
            ) {
                IconToggleButton(
                    checked = (selectedButton == index),
                    onCheckedChange = { selectedButton = index },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = text, fontSize = 13.sp)
                }
            }
        }
    }
}