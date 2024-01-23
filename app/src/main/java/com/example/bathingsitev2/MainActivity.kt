package com.example.bathingsitev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bathingsitev2.navigation.Navigation
import com.example.bathingsitev2.screens.MainScreen
import com.example.bathingsitev2.ui.theme.BathingSiteV2Theme

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
