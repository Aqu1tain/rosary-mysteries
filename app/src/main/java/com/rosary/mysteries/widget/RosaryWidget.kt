package com.rosary.mysteries.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.material3.ColorProviders
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.rosary.mysteries.MainActivity
import com.rosary.mysteries.R
import com.rosary.mysteries.data.AppPreferences
import com.rosary.mysteries.data.MysteriesRepository
import com.rosary.mysteries.domain.Mystery
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

private val Parchment = Color(0xFFFAF6F1)
private val Sepia = Color(0xFF2C1810)
private val GoldLeaf = Color(0xFFD4A84B)
private val WarmBrown = Color(0xFF5C4A3D)
private val LightGold = Color(0xFFF5EBD7)

private val Obsidian = Color(0xFF12100E)
private val AntiqueCream = Color(0xFFE8DFD2)
private val Candlelight = Color(0xFFE8C46D)
private val DarkBrown = Color(0xFF1E1915)
private val MutedGold = Color(0xFF2A2420)

private val LightColors = lightColorScheme(
    primary = Sepia,
    secondary = GoldLeaf,
    tertiary = WarmBrown,
    background = Parchment,
    surface = Parchment,
    surfaceVariant = LightGold,
    onPrimary = Parchment,
    onSecondary = Sepia,
    onBackground = Sepia,
    onSurface = WarmBrown,
    onSurfaceVariant = GoldLeaf
)

private val DarkColors = darkColorScheme(
    primary = AntiqueCream,
    secondary = Candlelight,
    tertiary = AntiqueCream,
    background = Obsidian,
    surface = DarkBrown,
    surfaceVariant = MutedGold,
    onPrimary = Obsidian,
    onSecondary = Obsidian,
    onBackground = AntiqueCream,
    onSurface = AntiqueCream,
    onSurfaceVariant = Candlelight
)

private val WidgetColors = ColorProviders(light = LightColors, dark = DarkColors)

class RosaryWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Responsive(
        setOf(
            DpSize(180.dp, 180.dp),
            DpSize(280.dp, 280.dp)
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val luminousEnabled = AppPreferences(context).getLuminousEnabledBlocking()
        val todaySet = MysteriesRepository.getToday(luminousEnabled)
        val title = context.getString(todaySet.titleResId)
        val todayLabel = context.getString(R.string.today_mysteries)

        provideContent {
            GlanceTheme(colors = WidgetColors) {
                WidgetContent(title, todayLabel, todaySet.mysteries, context)
            }
        }
    }
}

@Composable
private fun WidgetContent(
    title: String,
    todayLabel: String,
    mysteries: List<Mystery>,
    context: Context
) {
    val size = LocalSize.current
    val isLarge = size.width >= 280.dp

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .cornerRadius(20.dp)
            .background(GlanceTheme.colors.surface)
            .clickable(actionStartActivity<MainActivity>())
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(horizontal = if (isLarge) 20.dp else 16.dp, vertical = if (isLarge) 18.dp else 14.dp)
        ) {
            WidgetHeader(title, todayLabel, isLarge)

            Spacer(GlanceModifier.height(if (isLarge) 14.dp else 12.dp))

            Divider()

            Spacer(GlanceModifier.height(if (isLarge) 12.dp else 10.dp))

            Column(modifier = GlanceModifier.defaultWeight()) {
                mysteries.forEachIndexed { index, mystery ->
                    MysteryItem(mystery, context, isLarge)
                    if (index < mysteries.lastIndex) {
                        Spacer(GlanceModifier.height(if (isLarge) 8.dp else 6.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun WidgetHeader(title: String, todayLabel: String, isLarge: Boolean) {
    Row(
        modifier = GlanceModifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = GlanceModifier
                .size(if (isLarge) 40.dp else 32.dp)
                .cornerRadius(if (isLarge) 12.dp else 10.dp)
                .background(GlanceTheme.colors.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Image(
                provider = ImageProvider(R.drawable.ic_launcher_logo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = GlanceModifier.size(if (isLarge) 26.dp else 20.dp)
            )
        }

        Spacer(GlanceModifier.width(if (isLarge) 14.dp else 12.dp))

        Column(modifier = GlanceModifier.defaultWeight()) {
            Text(
                text = title,
                style = TextStyle(
                    color = GlanceTheme.colors.primary,
                    fontSize = if (isLarge) 18.sp else 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1
            )
            Spacer(GlanceModifier.height(2.dp))
            Text(
                text = todayLabel,
                style = TextStyle(
                    color = GlanceTheme.colors.onSurfaceVariant,
                    fontSize = if (isLarge) 12.sp else 11.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun Divider() {
    Spacer(
        modifier = GlanceModifier
            .fillMaxWidth()
            .height(1.dp)
            .background(GlanceTheme.colors.surfaceVariant)
    )
}

@Composable
private fun MysteryItem(mystery: Mystery, context: Context, isLarge: Boolean) {
    Row(
        modifier = GlanceModifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = GlanceModifier
                .size(if (isLarge) 24.dp else 20.dp)
                .cornerRadius(if (isLarge) 12.dp else 10.dp)
                .background(GlanceTheme.colors.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = mystery.number.toString(),
                style = TextStyle(
                    color = GlanceTheme.colors.onSurfaceVariant,
                    fontSize = if (isLarge) 12.sp else 11.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }

        Spacer(GlanceModifier.width(if (isLarge) 12.dp else 10.dp))

        Text(
            text = context.getString(mystery.titleResId),
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = if (isLarge) 14.sp else 13.sp,
                fontWeight = FontWeight.Normal
            ),
            maxLines = 1,
            modifier = GlanceModifier.defaultWeight()
        )
    }
}
