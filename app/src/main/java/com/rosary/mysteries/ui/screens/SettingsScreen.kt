package com.rosary.mysteries.ui.screens

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.Lucide
import com.rosary.mysteries.BuildConfig
import com.rosary.mysteries.R
import com.rosary.mysteries.data.ThemeMode
import com.rosary.mysteries.data.rememberAppPreferences
import com.rosary.mysteries.widget.RosaryWidget
import kotlinx.coroutines.launch
import java.util.Locale

private val languages = mapOf(
    "en" to "English",
    "fr" to "Français",
    "es" to "Español",
    "it" to "Italiano"
)

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var currentLocale by remember { mutableStateOf(getCurrentLocale(context)) }
    val appPreferences = rememberAppPreferences()
    val currentTheme by appPreferences.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
    val luminousEnabled by appPreferences.luminousEnabled.collectAsState(initial = true)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Header(stringResource(R.string.settings), onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            SettingsSection(stringResource(R.string.mysteries_section)) {
                ToggleItem(
                    label = stringResource(R.string.luminous_enabled),
                    description = stringResource(R.string.luminous_enabled_desc),
                    checked = luminousEnabled,
                    onCheckedChange = {
                        scope.launch {
                            appPreferences.setLuminousEnabled(it)
                            updateWidgets(context)
                        }
                    }
                )
            }

            SectionDivider()

            SettingsSection(stringResource(R.string.theme)) {
                ThemeMode.entries.forEach { mode ->
                    SelectableItem(
                        label = stringResource(mode.labelRes),
                        selected = currentTheme == mode,
                        onClick = { scope.launch { appPreferences.setThemeMode(mode) } }
                    )
                }
            }

            SectionDivider()

            SettingsSection(stringResource(R.string.language)) {
                languages.forEach { (code, name) ->
                    SelectableItem(
                        label = name,
                        selected = currentLocale == code,
                        onClick = {
                            setLocale(context, code)
                            currentLocale = code
                        }
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

private val ThemeMode.labelRes: Int
    get() = when (this) {
        ThemeMode.SYSTEM -> R.string.theme_system
        ThemeMode.LIGHT -> R.string.theme_light
        ThemeMode.DARK -> R.string.theme_dark
    }

@Composable
private fun Header(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                Lucide.ArrowLeft,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(Modifier.width(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Box(
                Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun SectionDivider() {
    Spacer(Modifier.height(24.dp))
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
        modifier = Modifier.padding(horizontal = 8.dp)
    )
    Spacer(Modifier.height(24.dp))
}

@Composable
private fun SelectableItem(label: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
        else MaterialTheme.colorScheme.surface,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "bg"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .background(backgroundColor)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        if (selected) {
            Icon(
                Lucide.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ToggleItem(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (checked) MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
        else MaterialTheme.colorScheme.surface,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "bg"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onCheckedChange(!checked) }
            )
            .background(backgroundColor)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = if (checked) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    if (checked) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    Lucide.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

private fun getCurrentLocale(context: Context): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val locales = context.getSystemService(LocaleManager::class.java)?.applicationLocales
        if (locales != null && !locales.isEmpty) return locales.get(0)?.language ?: Locale.getDefault().language
    }
    val locales = AppCompatDelegate.getApplicationLocales()
    if (!locales.isEmpty) return locales.get(0)?.language ?: Locale.getDefault().language
    return Locale.getDefault().language
}

private fun setLocale(context: Context, languageCode: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java)?.applicationLocales = LocaleList.forLanguageTags(languageCode)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }
}

private suspend fun updateWidgets(context: Context) {
    val manager = GlanceAppWidgetManager(context)
    val widget = RosaryWidget()
    manager.getGlanceIds(RosaryWidget::class.java).forEach { id ->
        widget.update(context, id)
    }
}
