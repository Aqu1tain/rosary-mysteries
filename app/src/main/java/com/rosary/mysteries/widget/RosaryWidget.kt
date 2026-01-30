package com.rosary.mysteries.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.rosary.mysteries.MainActivity
import com.rosary.mysteries.data.MysteriesRepository
import com.rosary.mysteries.domain.Mystery

class RosaryWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val todaySet = MysteriesRepository.getToday()
        val title = context.getString(todaySet.titleResId)

        provideContent {
            GlanceTheme {
                WidgetContent(title, todaySet.mysteries, context)
            }
        }
    }
}

@Composable
private fun WidgetContent(title: String, mysteries: List<Mystery>, context: Context) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .cornerRadius(16.dp)
            .background(GlanceTheme.colors.surface)
            .clickable(actionStartActivity<MainActivity>())
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = GlanceTheme.colors.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(GlanceModifier.height(12.dp))

        mysteries.forEach { mystery ->
            MysteryRow(mystery, context)
            Spacer(GlanceModifier.height(6.dp))
        }
    }
}

@Composable
private fun MysteryRow(mystery: Mystery, context: Context) {
    Row(
        modifier = GlanceModifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${mystery.number}.",
            style = TextStyle(
                color = GlanceTheme.colors.secondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(GlanceModifier.width(8.dp))
        Text(
            text = context.getString(mystery.titleResId),
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 13.sp
            )
        )
    }
}
