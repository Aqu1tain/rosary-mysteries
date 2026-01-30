package com.rosary.mysteries.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.key
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
import androidx.compose.ui.unit.sp
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
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                Lucide.Menu,
                contentDescription = stringResource(R.string.menu),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column(modifier = Modifier.weight(1f)) {
            AnimatedContent(
                targetState = title,
                transitionSpec = {
                    fadeIn(spring(stiffness = Spring.StiffnessLow)) togetherWith
                            fadeOut(spring(stiffness = Spring.StiffnessLow))
                },
                label = "title"
            ) { animatedTitle ->
                Text(
                    text = animatedTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            if (isToday) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.today_mysteries),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        letterSpacing = 1.sp
                    )
                }
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
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 28.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        allMysteries.forEach { set ->
            DrawerItem(
                title = stringResource(set.titleResId),
                isSelected = set.type == selectedSet.type,
                badge = if (set.type == todayType) stringResource(R.string.today_mysteries) else null,
                onClick = { onMysterySelect(set) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 28.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        DrawerIconItem(
            icon = { Icon(Lucide.BookOpen, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(20.dp)) },
            title = stringResource(R.string.how_to_pray),
            onClick = onHowToClick
        )

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 28.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        DrawerIconItem(
            icon = { Icon(Lucide.Heart, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp)) },
            title = stringResource(R.string.support),
            onClick = onSupportClick
        )

        DrawerIconItem(
            icon = { Icon(Lucide.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(20.dp)) },
            title = stringResource(R.string.settings),
            onClick = onSettingsClick
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun DrawerItem(title: String, isSelected: Boolean, badge: String?, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .background(
                if (isSelected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
                else MaterialTheme.colorScheme.surface
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
        )
        badge?.let {
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
private fun DrawerIconItem(icon: @Composable () -> Unit, title: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .padding(horizontal = 28.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MysteryList(selectedSet: MysterySet) {
    key(selectedSet.type) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(selectedSet.mysteries) { index, mystery ->
                MysteryCard(mystery = mystery, index = index)
                if (mystery.number < 5) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        modifier = Modifier.padding(start = 54.dp)
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
