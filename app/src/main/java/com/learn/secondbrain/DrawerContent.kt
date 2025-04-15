package com.learn.secondbrain


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun DrawerContent(
    navController: NavHostController,
    onCloseDrawer: () -> Unit,
    selectedTheme: AppTheme,
    onThemeChange: (AppTheme) -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxWidth(0.7f) // Sets drawer width to 70% of screen width
    ) {
        Spacer(Modifier.height(16.dp))
        NavigationDrawerItem(
            label = { Text("Home") },
            selected = false,
            onClick = {
                navController.navigate("home")
                onCloseDrawer()
            },
            icon = { Icon(Icons.Filled.Home, "Home") }
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            onClick = {
                navController.navigate("settings")
                onCloseDrawer()
            },
            icon = { Icon(Icons.Filled.Settings, "Settings") }
        )
        Divider(Modifier.padding(vertical = 8.dp))
        Text(
            "Theme",
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
        ThemeOptions(selectedTheme, onThemeChange)
    }
}










