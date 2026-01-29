package com.rosary.mysteries.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rosary.mysteries.ui.screens.HomeScreen
import com.rosary.mysteries.ui.screens.HowToScreen
import com.rosary.mysteries.ui.screens.SettingsScreen

object Routes {
    const val HOME = "home"
    const val SETTINGS = "settings"
    const val HOW_TO = "how_to"
}

@Composable
fun RosaryNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onHowToClick = { navController.navigate(Routes.HOW_TO) }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.HOW_TO) {
            HowToScreen(onBack = { navController.popBackStack() })
        }
    }
}
