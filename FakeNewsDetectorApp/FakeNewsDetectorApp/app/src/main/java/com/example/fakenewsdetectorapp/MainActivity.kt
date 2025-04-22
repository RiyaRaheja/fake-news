package com.example.fakenewsdetectorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fakenewsdetectorapp.ui.theme.FakeNewsDetectorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FakeNewsDetectorAppTheme {
                val navController = rememberNavController()
                var resultText by remember { mutableStateOf("") }
                var isFake by remember { mutableStateOf<Boolean?>(null) }

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen {
                            navController.navigate("input")
                        }
                    }

                    composable("input") {
                        InputScreen { newsText ->
                            predictFakeNews(newsText) { prediction ->
                                resultText = if (prediction == "0") "Fake News" else "Real News"
                                isFake = prediction == "0"  // true if Fake, false if Real
                                navController.navigate("result")
                            }
                        }
                    }

                    composable("result") {
                        ResultScreen(result = resultText) {
                            navController.popBackStack("home", inclusive = false)
                        }
                    }
                }
            }
        }
    }

    private fun predictFakeNews(newsText: String, onResult: (String) -> Unit) {
        val request = NewsRequest(newsText)

        RetrofitInstance.apiService.getPrediction(request).enqueue(object : retrofit2.Callback<PredictionResponse> {
            override fun onResponse(call: retrofit2.Call<PredictionResponse>, response: retrofit2.Response<PredictionResponse>) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.prediction ?: ""
                    onResult(prediction)  // "0" or "1" returned from the backend
                } else {
                    onResult("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<PredictionResponse>, t: Throwable) {
                onResult("Failed: ${t.message}")
            }
        })
    }
}
