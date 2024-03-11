package com.example.newmobileapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.newmobileapp.presentation.ui.screens.CartScreen
import com.example.newmobileapp.presentation.ui.screens.FavoritesScreen
import com.example.newmobileapp.presentation.ui.screens.HomeScreen
import com.example.newmobileapp.presentation.viewmodels.NetworkStateViewModel
import com.example.newmobileapp.util.NavActions
import com.example.newmobileapp.util.NavRoutes
import com.example.newmobileapp.util.NetworkConnectionState
import com.example.newmobileapp.util.NetworkStatusView
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.rememberKoinInject

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

                MainScreen(navActions, coroutineScope)

            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navActions: NavActions, coroutineScope: CoroutineScope) {

    //navigation vars
    val currentBackStackEntry by navActions.navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: NavRoutes.HOME

    //network state vars
    val networkStateViewModel: NetworkStateViewModel = rememberKoinInject()
    val networkState by networkStateViewModel.networkState.collectAsState()


    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(Modifier
            .fillMaxSize(), bottomBar = {
            NetworkStatusView(networkState == NetworkConnectionState.Available)
        }) {


            NavHost(
                navController = navActions.navController,
                startDestination = NavRoutes.HOME
            ) {
                composable(NavRoutes.HOME) {
                    HomeScreen(
                        currentRoute = currentRoute,
                        navActions = navActions,
                        coroutineScope = coroutineScope
                    )
                }
                composable(NavRoutes.CART) {
                    CartScreen(
                        currentRoute = currentRoute,
                        navActions = navActions,
                        coroutineScope = coroutineScope
                    )
                }
                composable(NavRoutes.FAVORITES) {
                    FavoritesScreen(
                        currentRoute = currentRoute,
                        navActions = navActions,
                        coroutineScope = coroutineScope
                    )
                }

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