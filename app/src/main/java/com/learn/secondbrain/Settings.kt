package com.learn.secondbrain
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Application class
class SecondBrainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SettingsManager.init(applicationContext)
    }
}

// Settings Manager for handling preferences
object SettingsManager {
    private const val PREFS_NAME = "SecondBrainPrefs"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_FAVORITES = "favorite_books"

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadFavorites()
    }

    // Dark Mode settings
    fun isDarkMode(): Boolean = prefs.getBoolean(KEY_DARK_MODE, false)

    fun setDarkMode(isDark: Boolean) {
        prefs.edit {
            putBoolean(KEY_DARK_MODE, isDark)
        }
    }

    // Favorites management
    private fun loadFavorites() {
        val favoritesJson = prefs.getString(KEY_FAVORITES, null)
        favoritesJson?.let {
            try {
                val type = object : TypeToken<Set<String>>() {}.type
                val loadedFavorites: Set<String> = gson.fromJson(it, type)
                BookData.setFavorites(loadedFavorites)
            } catch (e: Exception) {
                BookData.setFavorites(emptySet())
            }
        }
    }

    fun saveFavorites(favorites: Set<String>) {
        prefs.edit {
            putString(KEY_FAVORITES, gson.toJson(favorites))
        }
    }
}



@Composable
fun ThemeOptions(selectedTheme: AppTheme, onThemeChange: (AppTheme) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Dark Theme")
        Switch(
            checked = selectedTheme == AppTheme.DARK,
            onCheckedChange = { isChecked ->
                onThemeChange(if (isChecked) AppTheme.DARK else AppTheme.LIGHT)
            }
        )
    }
}









/*
// Navigation setup
@Composable
fun AppNavigation() {
    var isDarkMode by remember { mutableStateOf(SettingsManager.isDarkMode()) }
    val navController = rememberNavController()

    MaterialTheme(
        colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController = navController)
            }
            composable("book/{bookId}") { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId") ?: "1"
                BookDetailScreen(bookId = bookId, navController = navController)
            }
            composable("settings") {
                SettingsScreen(
                    isDarkMode = isDarkMode,
                    onThemeChanged = {
                        isDarkMode = it
                        SettingsManager.setDarkMode(it)
                    }
                )
            }
        }
    }
}             */

// Settings Screen
@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Dark Mode",
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = onThemeChanged
            )
        }
    }
}

