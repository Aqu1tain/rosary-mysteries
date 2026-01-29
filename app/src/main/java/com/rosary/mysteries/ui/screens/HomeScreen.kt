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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
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
private fun Header(
    title: String,
    isToday: Boolean,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                Icons.Default.Menu,
                contentDescription = stringResource(R.string.menu),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (isToday) {
                Text(
                    text = stringResource(R.string.today_mysteries),
                    style = MaterialTheme.typography.bodySmall,
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
        drawerContainerColor = MaterialTheme.colorScheme.background
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        allMysteries.forEach { set ->
            DrawerItem(
                title = stringResource(set.titleResId),
                isSelected = set.type == selectedSet.type,
                isToday = set.type == todayType,
                onClick = { onMysterySelect(set) }
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onHowToClick)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.how_to_pray),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onSupportClick)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.support),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onSettingsClick)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun DrawerItem(
    title: String,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                if (isSelected) MaterialTheme.colorScheme.surfaceVariant
                else MaterialTheme.colorScheme.background
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
        if (isToday) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "*",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun MysteryList(selectedSet: MysterySet) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(selectedSet.mysteries) { mystery ->
            MysteryCard(mystery)
            if (mystery.number < 5) {
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
