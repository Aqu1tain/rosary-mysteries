package com.rosary.mysteries.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.BookOpen
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Settings
import com.rosary.mysteries.R
import com.rosary.mysteries.data.MysteriesRepository
import com.rosary.mysteries.domain.MysterySet
import com.rosary.mysteries.domain.MysteryType
import com.rosary.mysteries.ui.components.MysteryCard
import kotlinx.coroutines.launch

private const val KOFI_URL = "https://ko-fi.com/corentin_r"

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onHowToClick: () -> Unit
) {
    val allMysteries = remember { MysteriesRepository.getAll() }
    val todayType = remember { MysteryType.today() }
    var selectedSet by remember { mutableStateOf(MysteriesRepository.getToday()) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                allMysteries = allMysteries,
                selectedSet = selectedSet,
                todayType = todayType,
                onMysterySelect = {
                    selectedSet = it
                    scope.launch { drawerState.close() }
                },
                onHowToClick = {
                    scope.launch { drawerState.close() }
                    onHowToClick()
                },
                onSettingsClick = {
                    scope.launch { drawerState.close() }
                    onSettingsClick()
                },
                onSupportClick = {
                    scope.launch { drawerState.close() }
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(KOFI_URL)))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            Header(
                title = stringResource(selectedSet.titleResId),
                isToday = selectedSet.type == todayType,
                onMenuClick = { scope.launch { drawerState.open() } }
            )
            MysteryList(selectedSet)
        }
    }
}

@Composable
private fun Header(title: String, isToday: Boolean, onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                Lucide.Menu,
                contentDescription = stringResource(R.string.menu),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            if (isToday) {
                Text(
                    text = stringResource(R.string.today_mysteries),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun DrawerContent(
    allMysteries: List<MysterySet>,
    selectedSet: MysterySet,
    todayType: MysteryType,
    onMysterySelect: (MysterySet) -> Unit,
    onHowToClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onSupportClick: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        Spacer(modifier = Modifier.height(8.dp))

        allMysteries.forEach { set ->
            DrawerItem(
                title = stringResource(set.titleResId),
                isSelected = set.type == selectedSet.type,
                badge = if (set.type == todayType) stringResource(R.string.today_mysteries) else null,
                onClick = { onMysterySelect(set) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(8.dp))

        DrawerIconItem(
            icon = { Icon(Lucide.BookOpen, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary) },
            title = stringResource(R.string.how_to_pray),
            onClick = onHowToClick
        )

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        DrawerIconItem(
            icon = { Icon(Lucide.Heart, contentDescription = null, tint = MaterialTheme.colorScheme.secondary) },
            title = stringResource(R.string.support),
            onClick = onSupportClick
        )

        DrawerIconItem(
            icon = { Icon(Lucide.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary) },
            title = stringResource(R.string.settings),
            onClick = onSettingsClick
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DrawerItem(title: String, isSelected: Boolean, badge: String?, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface)
            .padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        badge?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun DrawerIconItem(icon: @Composable () -> Unit, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun MysteryList(selectedSet: MysterySet) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(selectedSet.mysteries) { mystery ->
            MysteryCard(mystery)
            if (mystery.number < 5) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
