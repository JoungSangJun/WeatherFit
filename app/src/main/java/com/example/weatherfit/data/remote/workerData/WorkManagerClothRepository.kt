package com.example.weatherfit.data.remote.workerData

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherfit.worker.ClothRecommendWorker

class WorkManagerClothRepository(context: Context) : ClothRecommendRepository {
    private val workManager = WorkManager.getInstance(context)

    override fun recommendCloth(question: String, callback: (String) -> Unit) {
        val data = Data.Builder()
        data.putString("Question", question)
        val workRequestBuilder =
            OneTimeWorkRequestBuilder<ClothRecommendWorker>().setInputData(data.build()).build()

        workManager.enqueue(workRequestBuilder)
    }
}