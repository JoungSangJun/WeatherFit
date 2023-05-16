package com.example.weatherfit.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object CurrentTime {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH")
    val currentTime = current.format(formatter) + "00"
    val year = current.year.toString()
    val month = "%02d".format(current.monthValue)
    val day = "%02d".format(current.dayOfMonth)
    val today = year + month + day
}