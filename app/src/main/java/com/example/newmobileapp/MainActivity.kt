package com.example.newmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newmobileapp.presentation.theme.DigitalStoreAppTheme
import com.example.newmobileapp.presentation.ui.screens.AuthScreen
import com.example.newmobileapp.presentation.ui.screens.HomeScreen
import com.example.newmobileapp.util.NavActions
import com.example.newmobileapp.util.NavRoutes
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DigitalStoreAppTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                val navActions = remember(navController) { NavActions(navController) }
                val coroutineScope = rememberCoroutineScope()

                MainScreen(navActions,coroutineScope)

            }
        }
    }
}

@Composable
fun MainScreen(navActions: NavActions, coroutineScope: CoroutineScope) {
    val currentBackStackEntry by navActions.navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: NavRoutes.HOME

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navActions.navController,
            startDestination = NavRoutes.HOME
        ) {
            composable(NavRoutes.HOME) {
                HomeScreen(currentRoute,navActions,coroutineScope)
            }
            composable(NavRoutes.CART) {
                AuthScreen()
            }
            composable(NavRoutes.FAVORITES) {
                // TODO:
            }
            composable(NavRoutes.FAVORITES) {
                // TODO:

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DigitalStoreAppTheme {
        Greeting("Android")
    }
}