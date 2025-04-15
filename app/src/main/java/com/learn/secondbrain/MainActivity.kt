package com.learn.secondbrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import java.util.Locale
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

enum class AppTheme {
    LIGHT, DARK, SYSTEM
}

@Composable
fun MyAppTheme(
    appTheme: AppTheme,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appTheme) {
        AppTheme.LIGHT -> lightColorScheme(
            primary = Color(0xFF11453C),
            secondary = Color(0xFFFFFFFF),
            background = Color(0xFFFFFFFF),
            surface = Color(0xFFF1EBC8),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF1A1A1A),
            onSurface = Color(0xFF000000)
        )
        AppTheme.DARK -> darkColorScheme(
            primary = Color(0xFF092B25),
            secondary = Color(0xFFEDEDED),
            background = Color(0xFF051614),
            surface = Color(0xFF1E1E1E),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
        AppTheme.SYSTEM -> if (isSystemInDarkTheme()) darkColorScheme(
            primary = Color(0xFF092B25),
            secondary = Color(0xFFEDEDED),
            background = Color(0xFF051614),
            surface = Color(0xFF1E1E1E),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        ) else lightColorScheme(
            primary = Color(0xFF11453C),
            secondary = Color(0xFFFFFFFF),
            background = Color(0xFFFFFFFF),
            surface = Color(0xFFF1EBC8),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF1A1A1A),
            onSurface = Color(0xFF000000)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTheme by rememberSaveable { mutableStateOf(AppTheme.SYSTEM) }

    MyAppTheme(appTheme = selectedTheme) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    navController = navController,
                    onCloseDrawer = { scope.launch { drawerState.close() } },
                    selectedTheme = selectedTheme,
                    onThemeChange = { theme -> selectedTheme = theme }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Second Brain 🧠") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, "Menu")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                bottomBar = { BottomNavigationBar(navController) }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("home") { HomeScreen(navController) }
                    composable("explore") { ExploreScreen() }
                    composable("library") { LibraryScreen(navController) }

                    composable("settings") { SettingsScreen(selectedTheme) }
                    composable("book/{bookId}") { backStackEntry ->
                        val bookId = backStackEntry.arguments?.getString("bookId")
                        BookTopicsScreen(bookId ?: "")
                    }
                }
            }
        }
    }
}






