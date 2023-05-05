package com.example.weatherfit.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val response: Body
)

@Serializable
data class Body(
    val body: Items
)

@Serializable
data class Items(
    val items: Item
)

@Serializable
data class Item(
    val item: List<Hi>
)

@Serializable
data class Hi(
    val nx: Int,
    val ny: Int,
)