package com.example.bathingsitev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bathingsitev2.navigation.Navigation
import com.example.bathingsitev2.ui.theme.BathingSiteV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BathingSiteV2Theme {
                Navigation()
            }

        }
    }
}
