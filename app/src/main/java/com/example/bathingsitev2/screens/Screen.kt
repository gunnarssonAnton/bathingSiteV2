package com.example.bathingsitev2.screens

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object AddBathingSiteScreen: Screen("add_bathing_site_screen")
}
