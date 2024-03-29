package com.example.bathingsitev2.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.bathingsitev2.views.BathingSiteView

@Composable
fun MainScreen(navController: NavHostController) {
        Scaffold(floatingActionButton = {
            Fab {
                navController.navigate(Screen.AddBathingSiteScreen.route)
            }
        }) {
            BathingSiteView(
                Modifier,
                viewOnClick = {navController.navigate(Screen.AllBathingSitesScreen.route)}
            )
        }




}

@Composable
fun Fab(onClick: ()-> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Floating Action button")
    }
}