package com.rosary.mysteries.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.rosary.mysteries.R
import java.util.Locale

@Composable
fun PrayersScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Header(onBack)
        PrayersList()
    }
}

@Composable
private fun Header(onBack: () -> Unit) {
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
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.prayers),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun PrayersList() {
    val locale = remember { Locale.getDefault().language }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp)
    ) {
        if (locale == "es") {
            PrayerCard(stringResource(R.string.sign_of_cross_title), stringResource(R.string.sign_of_cross_text))
            PrayerDivider()
        }

        PrayerCard(stringResource(R.string.apostles_creed_title), stringResource(R.string.apostles_creed_text))
        PrayerDivider()
        PrayerCard(stringResource(R.string.our_father_title), stringResource(R.string.our_father_text))
        PrayerDivider()
        PrayerCard(stringResource(R.string.hail_mary_title), stringResource(R.string.hail_mary_text))
        PrayerDivider()
        PrayerCard(stringResource(R.string.glory_be_title), stringResource(R.string.glory_be_text))
        PrayerDivider()
        PrayerCard(stringResource(R.string.fatima_prayer_title), stringResource(R.string.fatima_prayer_text))
        PrayerDivider()

        if (locale == "es") {
            PrayerCard(stringResource(R.string.maria_madre_title), stringResource(R.string.maria_madre_text))
            PrayerDivider()
        }

        if (locale == "it") {
            PrayerCard(stringResource(R.string.maria_regina_title), stringResource(R.string.maria_regina_text))
            PrayerDivider()
        }

        PrayerCard(stringResource(R.string.hail_holy_queen_title), stringResource(R.string.hail_holy_queen_text))

        if (locale == "en") {
            PrayerDivider()
            PrayerCard(stringResource(R.string.closing_prayer_title), stringResource(R.string.closing_prayer_text))
        }

        if (locale == "it") {
            PrayerDivider()
            PrayerCard(stringResource(R.string.st_michael_title), stringResource(R.string.st_michael_text))
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun PrayerCard(title: String, text: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 26.sp,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}

@Composable
private fun PrayerDivider() {
    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
    Spacer(modifier = Modifier.height(24.dp))
}
