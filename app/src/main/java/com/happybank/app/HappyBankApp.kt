package com.happybank.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.happybank.app.ui.screen.HomeScreen
import com.happybank.app.ui.screen.ServicesScreen

@Composable
fun HappyBankApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToSecond = {
                    navController.navigate("services")
                }
            )
        }
        composable("services") {
            ServicesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}