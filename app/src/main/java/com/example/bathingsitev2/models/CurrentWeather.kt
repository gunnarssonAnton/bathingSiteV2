package com.example.bathingsitev2.models

import androidx.compose.ui.graphics.ImageBitmap

data class CurrentWeather(
    val imageBitmap: ImageBitmap,
    val description: String,
    val temp: String,
)