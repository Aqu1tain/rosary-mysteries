package com.rosary.mysteries.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosary.mysteries.R
import com.rosary.mysteries.data.MysteriesRepository
import com.rosary.mysteries.domain.MysterySet
import com.rosary.mysteries.domain.MysteryType
import com.rosary.mysteries.ui.components.MysteryCard
import com.rosary.mysteries.ui.components.MysteryTypeChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onSupportClick: () -> Unit
) {
    val allMysteries = remember { MysteriesRepository.getAll() }
    val todayType = remember { MysteryType.today() }
    var selectedSet by remember { mutableStateOf(MysteriesRepository.getToday()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onSupportClick) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = stringResource(R.string.support),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            MysteryTypeSelector(
                allMysteries = allMysteries,
                selectedSet = selectedSet,
                todayType = todayType,
                onSelect = { selectedSet = it }
            )

            MysteryList(selectedSet)
        }
    }
}

@Composable
private fun MysteryTypeSelector(
    allMysteries: List<MysterySet>,
    selectedSet: MysterySet,
    todayType: MysteryType,
    onSelect: (MysterySet) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(allMysteries) { set ->
                MysteryTypeChip(
                    mysterySet = set,
                    isSelected = set.type == selectedSet.type,
                    onClick = { onSelect(set) }
                )
            }
        }

        if (selectedSet.type == todayType) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.today_mysteries),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun MysteryList(selectedSet: MysterySet) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = stringResource(selectedSet.titleResId),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(selectedSet.mysteries) { mystery ->
            MysteryCard(mystery)
        }
    }
}
