package com.example.fakenewsdetectorapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

@Composable
fun ResultScreen(result: String, onBack: () -> Unit) {
    // Determine the background color based on the result text
    val bgColor = if (result == "Fake News") Color.Red else Color.Green

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Prediction Result:",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = result,  // Display either "Fake News" or "Real News"
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onBack) {
                Text("Go Back")
            }
        }
    }
}
