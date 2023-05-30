package com.example.weatherfit.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ClothRecommendWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val question = inputData.getString("Question")
        Log.d("testt", question.toString())
        return Result.success()
    }
}