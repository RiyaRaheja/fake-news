package com.example.fakenewsdetectorapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Define the data class for the API response
data class PredictionResponse(
    val prediction: String
)

// Define the data class for the API request
data class NewsRequest(
    val text: String
)

interface ApiService {

    @POST("predict")
    fun getPrediction(@Body request: NewsRequest): Call<PredictionResponse>
}
