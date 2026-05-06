package com.offmind.aiflappybird

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.offmind.aiflappybird.presentation.game.GameScreen

object AppDestinations {
    const val WELCOME = "welcome"
    const val GAME = "game"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppDestinations.WELCOME,
    ) {
        composable(AppDestinations.WELCOME) {
            WelcomeScreen(
                onStartGame = {
                    navController.navigate(AppDestinations.GAME) {
                        launchSingleTop = true
                    }
                },
            )
        }
        composable(AppDestinations.GAME) {
            GameScreen()
        }
    }
}
