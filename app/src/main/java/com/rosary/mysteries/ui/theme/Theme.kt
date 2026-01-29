package com.rosary.mysteries.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat

private val Ivory = Color(0xFFFAF8F5)
private val WarmWhite = Color(0xFFFFFEFC)
private val DeepBrown = Color(0xFF3D2914)
private val WarmBrown = Color(0xFF5D4037)
private val GoldAccent = Color(0xFFF0AB20)
private val SoftGray = Color(0xFF8B7355)
private val LightTan = Color(0xFFF5EDE4)

private val DarkBackground = Color(0xFF1A1612)
private val DarkSurface = Color(0xFF2D2520)
private val LightText = Color(0xFFF5EDE4)

private val LightColorScheme = lightColorScheme(
    primary = DeepBrown,
    onPrimary = WarmWhite,
    secondary = GoldAccent,
    onSecondary = DeepBrown,
    tertiary = SoftGray,
    background = Ivory,
    onBackground = DeepBrown,
    surface = WarmWhite,
    onSurface = DeepBrown,
    surfaceVariant = LightTan,
    onSurfaceVariant = WarmBrown,
    outline = Color(0xFFD4C4B0)
)

private val DarkColorScheme = darkColorScheme(
    primary = LightText,
    onPrimary = DarkBackground,
    secondary = GoldAccent,
    onSecondary = DarkBackground,
    tertiary = SoftGray,
    background = DarkBackground,
    onBackground = LightText,
    surface = DarkSurface,
    onSurface = LightText,
    surfaceVariant = Color(0xFF3D332B),
    onSurfaceVariant = Color(0xFFB8A99A),
    outline = Color(0xFF5D4D40)
)

private val AppTypography = Typography()

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
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
