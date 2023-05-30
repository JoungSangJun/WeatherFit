package com.example.weatherfit.data.remote.workerData

import android.content.Context

interface ClothRecommendAppContainer {
    val clothRecommendRepository: ClothRecommendRepository
}

class ClothAppContainer(
    context: Context,
) : ClothRecommendAppContainer {
    override val clothRecommendRepository: WorkManagerClothRepository =
        WorkManagerClothRepository(context)
}