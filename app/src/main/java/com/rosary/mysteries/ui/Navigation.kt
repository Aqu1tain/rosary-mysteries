package com.rosary.mysteries.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rosary.mysteries.ui.screens.HomeScreen
import com.rosary.mysteries.ui.screens.SettingsScreen

object Routes {
    const val HOME = "home"
    const val SETTINGS = "settings"
}

private const val KOFI_URL = "https://ko-fi.com/rosarymysteries"

@Composable
fun RosaryNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onSupportClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(KOFI_URL)))
                }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
