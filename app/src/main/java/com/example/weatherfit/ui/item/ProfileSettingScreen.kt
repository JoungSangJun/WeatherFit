package com.example.weatherfit.ui.item

import android.content.Context
import android.widget.Toast
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
    context: Context
) {
    ProfileSettingScreen(
        uiState = viewModel.uiState.collectAsState().value,
        saveUserProfilePreference = viewModel::saveUserProfilePreference,
        context = context,
        modifier = modifier
    )
}

@Composable
fun ProfileSettingScreen(
    uiState: UserProfileUiState,
    saveUserProfilePreference: (UserProfileUiState) -> Unit,
    context: Context,
    modifier: Modifier
) {
    var textFiledName by remember { mutableStateOf(uiState.name) }
    var textFiledAge by remember { mutableStateOf(uiState.age) }
    var selectedSexOption by remember { mutableStateOf(uiState.sex) }
    var selectedPurposeOption by remember { mutableStateOf(uiState.purpose) }
    var selectedBodyTypeOption by remember { mutableStateOf(uiState.bodyType) }
    var selectedPreferredStyleOption by remember { mutableStateOf(uiState.preferredStyle) }


//  처음 name,age의 값은 빈 값이 들어온 직후 uiState: StateFlow<UserProfileUiState>가 불러온 dataStore의
//  값으로 변경되는데 그때 textFiledName의 값을 name으로 받기 위함
    LaunchedEffect(uiState.name) {
        textFiledName = uiState.name
    }
    LaunchedEffect(uiState.age) {
        textFiledAge = uiState.age
    }
    LaunchedEffect(uiState.sex) {
        selectedSexOption = uiState.sex
    }
    LaunchedEffect(uiState.purpose) {
        selectedPurposeOption = uiState.purpose
    }
    LaunchedEffect(uiState.bodyType) {
        selectedBodyTypeOption = uiState.bodyType
    }
    LaunchedEffect(uiState.preferredStyle) {
        selectedPreferredStyleOption = uiState.preferredStyle
    }

    Column() {
        // 상태 호이스팅 패턴 적용
        UserProfileScreen(
            textFiledName,
            onNameValueChange = { textFiledName = it },
            textFiledAge,
            onAgeValueChange = { textFiledAge = it },
            selectedSexOption,
            onSexValueChange = { selectedSexOption = it }
        )
        UserClothStyleScreen(
            selectedPurposeOption,
            onPurposeValueChange = { selectedPurposeOption = it },
            selectedBodyTypeOption,
            onBodyTypeValueChange = { selectedBodyTypeOption = it },
            selectedPreferredStyleOption,
            onPreferredStyleValueChange = { selectedPreferredStyleOption = it }
        )
        Spacer(modifier = Modifier.weight(1f))
        // 저장하기 버튼
        Button(modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.LightGray,
        ), onClick = {
            saveUserProfilePreference(
                UserProfileUiState(
                    textFiledName,
                    textFiledAge,
                    selectedSexOption,
                    selectedPurposeOption,
                    selectedBodyTypeOption,
                    selectedPreferredStyleOption
                )
            )
            Toast.makeText(context, "저장되었습니다", Toast.LENGTH_SHORT).show()
        }) {
            Text("저장하기")
        }
    }
}

@Composable
fun UserProfileScreen(
    name: String,
    onNameValueChange: (String) -> Unit,
    age: String,
    onAgeValueChange: (String) -> Unit,
    sex: String,
    onSexValueChange: (String) -> Unit
) {
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
                value = name,
                onValueChange = onNameValueChange,
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
                value = age,
                onValueChange = onAgeValueChange,
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
            SexDropDownMenu(
                modifier = Modifier.weight(1f),
                sex = sex,
                onSexValueChange = onSexValueChange
            )
        }
    }
}

@Composable
fun SexDropDownMenu(
    modifier: Modifier,
    sex: String,
    onSexValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val options = listOf("남자", "여자")

    Column(modifier = modifier) {
        Text(
            text = sex,
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
                    onSexValueChange(option)
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun UserClothStyleScreen(
    selectedPurposeOption: String,
    onPurposeValueChange: (String) -> Unit,
    selectedBodyTypeOption: String,
    onBodyTypeValueChange: (String) -> Unit,
    selectedPreferredStyleOption: String,
    onPreferredStyleValueChange: (String) -> Unit,
) {
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
        IconSelectorScreen(
            selectedValue1 = "운동",
            selectedValue2 = "데이트",
            selectedValue3 = "비지니스",
            selectedValue4 = "결혼식",
            selectedPurposeOption,
            onPurposeValueChange
        )
        Text("체형", modifier = Modifier.padding(start = 30.dp), fontWeight = FontWeight.Bold)
        IconSelectorScreen(
            selectedValue1 = "삼각형",
            selectedValue2 = "역삼각형",
            selectedValue3 = "사각형",
            selectedValue4 = "타원형",
            selectedBodyTypeOption,
            onBodyTypeValueChange
        )
        Text("선호하는 스타일", modifier = Modifier.padding(start = 30.dp), fontWeight = FontWeight.Bold)
        IconSelectorScreen(
            selectedValue1 = "클래식",
            selectedValue2 = "캐주얼",
            selectedValue3 = "스트릿",
            selectedValue4 = "빈티지",
            selectedPreferredStyleOption,
            onPreferredStyleValueChange
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
    selectedOption: String,
    onValueChange: (String) -> Unit,
) {
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
                        selected = (selectedOption == text),
                        onClick = { onValueChange(text) }
                    )
                    .padding(horizontal = 5.dp),
                shape = CircleShape,
                border = if (selectedOption == text) BorderStroke(
                    2.dp,
                    Color.Blue
                ) else BorderStroke(2.dp, Color.Black)
            ) {
                IconToggleButton(
                    checked = (selectedOption == text),
                    onCheckedChange = { onValueChange(text) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = text, fontSize = 13.sp)
                }
            }
        }
    }
}