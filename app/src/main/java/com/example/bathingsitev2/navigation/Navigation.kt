package com.example.bathingsitev2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bathingsitev2.screens.MainScreen
import com.example.bathingsitev2.screens.Screen
import com.example.bathingsitev2.screens.AddBathingSiteScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route){
        composable(
            route = Screen.MainScreen.route
        ){
            MainScreen(navController)
        }
        composable(
            route = Screen.AddBathingSiteScreen.route
        ){
            AddBathingSiteScreen(navController)
        }
    }
}