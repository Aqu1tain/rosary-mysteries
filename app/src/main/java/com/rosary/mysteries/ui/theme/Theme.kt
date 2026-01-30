package com.rosary.mysteries.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.compose.material3.Typography
import com.rosary.mysteries.R

val Lora = FontFamily(
    Font(R.font.lora_regular, FontWeight.Normal),
    Font(R.font.lora_medium, FontWeight.Medium),
    Font(R.font.lora_semibold, FontWeight.SemiBold),
    Font(R.font.lora_bold, FontWeight.Bold),
    Font(R.font.lora_italic, FontWeight.Normal, FontStyle.Italic),
)

val CrimsonText = FontFamily(
    Font(R.font.crimson_text_regular, FontWeight.Normal),
    Font(R.font.crimson_text_semibold, FontWeight.SemiBold),
    Font(R.font.crimson_text_bold, FontWeight.Bold),
    Font(R.font.crimson_text_italic, FontWeight.Normal, FontStyle.Italic),
)

private val Parchment = Color(0xFFFDF8F3)
private val Cream = Color(0xFFFFFDF9)
private val Sepia = Color(0xFF2C1810)
private val WarmSepia = Color(0xFF4A3728)
private val GoldLeaf = Color(0xFFD4A84B)
private val Umber = Color(0xFF6B5344)
private val PaleGold = Color(0xFFF5E6C8)
private val Vellum = Color(0xFFF0E6D8)

private val Obsidian = Color(0xFF0F0D0B)
private val DarkLeather = Color(0xFF1C1714)
private val Candlelight = Color(0xFFEAC87C)
private val AntiqueCream = Color(0xFFE5D9C9)
private val WarmAmber = Color(0xFFCFB896)
private val Mahogany = Color(0xFF3D2E26)

private val LightColorScheme = lightColorScheme(
    primary = Sepia,
    onPrimary = Cream,
    secondary = GoldLeaf,
    onSecondary = Sepia,
    tertiary = Umber,
    background = Parchment,
    onBackground = Sepia,
    surface = Cream,
    onSurface = Sepia,
    surfaceVariant = Vellum,
    onSurfaceVariant = WarmSepia,
    outline = Color(0xFFD9CDBF),
    outlineVariant = PaleGold
)

private val DarkColorScheme = darkColorScheme(
    primary = AntiqueCream,
    onPrimary = Obsidian,
    secondary = Candlelight,
    onSecondary = Obsidian,
    tertiary = WarmAmber,
    background = Obsidian,
    onBackground = AntiqueCream,
    surface = DarkLeather,
    onSurface = AntiqueCream,
    surfaceVariant = Mahogany,
    onSurfaceVariant = WarmAmber,
    outline = Color(0xFF4A3D34),
    outlineVariant = Color(0xFF2E2520)
)

private val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Bold,
        fontSize = 44.sp,
        lineHeight = 52.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        lineHeight = 38.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.15.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = CrimsonText,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = CrimsonText,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = CrimsonText,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),
    bodySmall = TextStyle(
        fontFamily = CrimsonText,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp
    ),
    labelLarge = TextStyle(
        fontFamily = CrimsonText,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.4.sp
    ),
    labelMedium = TextStyle(
        fontFamily = CrimsonText,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = CrimsonText,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
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
