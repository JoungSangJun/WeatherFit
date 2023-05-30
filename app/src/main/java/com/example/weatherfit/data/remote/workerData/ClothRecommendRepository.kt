package com.example.weatherfit.data.remote.workerData

interface ClothRecommendRepository {
    fun recommendCloth(question: String, callback: (String) -> Unit)
}