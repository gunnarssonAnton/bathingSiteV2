package com.example.bathingsitev2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bathingsitev2.screens.*

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
        composable(
            route = Screen.SettingsScreen.route
        ){
            SettingsScreen(navHostController = navController)
        }
        composable(
            route = Screen.AllBathingSitesScreen.route
        ){
            AllBathingSitesScreen(navController = navController)
        }
        composable(
            route = Screen.DownloadScreen.route
        ){
            DownloadScreen(navHostController = navController)
        }
    }
}