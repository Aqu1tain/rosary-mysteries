package com.rosary.mysteries.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = DeepBlue,
    onPrimary = Cream,
    primaryContainer = RoyalBlue,
    onPrimaryContainer = Cream,
    secondary = Gold,
    onSecondary = DeepBlue,
    secondaryContainer = GoldLight,
    onSecondaryContainer = DeepBlue,
    background = Cream,
    onBackground = DeepBlue,
    surface = Cream,
    onSurface = DeepBlue,
    surfaceVariant = LightBlue,
    onSurfaceVariant = DeepBlue
)

private val DarkColorScheme = darkColorScheme(
    primary = LightBlue,
    onPrimary = DeepBlue,
    primaryContainer = RoyalBlue,
    onPrimaryContainer = Cream,
    secondary = Gold,
    onSecondary = DarkBackground,
    secondaryContainer = Gold,
    onSecondaryContainer = DarkBackground,
    background = DarkBackground,
    onBackground = Cream,
    surface = DarkSurface,
    onSurface = Cream,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = LightBlue
)

@Composable
fun RosaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
