package com.example.weatherfit.data.remote.chatGpt

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

interface ChatGptRepository {
    suspend fun getRecommendCloth(question: String): String
}

class NetworkChatGptRepository : ChatGptRepository {
    private val client = OkHttpClient()

    override suspend fun getRecommendCloth(question: String): String {
        val apiKey = "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"
        val url = "https://api.openai.com/v1/engines/text-davinci-003/completions"
        lateinit var recommendCloth: String

        val requestBody = """
            {
            "prompt": "$question",
            "max_tokens": 500,
            "temperature": 0
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
        val body = response.body?.string()


        if (body != null) {
            Log.v("data", body)
            val jsonObject = JSONObject(body)
            val jsonArray: JSONArray = jsonObject.getJSONArray("choices")
            recommendCloth = jsonArray.getJSONObject(0).getString("text")
        } else {
            Log.v("data", "empty")
            recommendCloth = "오류 발생 재시도"
        }

        return recommendCloth
    }
}