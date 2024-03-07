package com.example.newmobileapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newmobileapp.domain.Product
import com.example.newmobileapp.presentation.ui.appbar.TopBar
import com.example.newmobileapp.presentation.ui.components.Productcard
import com.example.newmobileapp.presentation.ui.utils.Drawer
import com.example.newmobileapp.presentation.viewmodels.FavoritesViewmodel
import com.example.newmobileapp.util.NavActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.rememberKoinInject


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritesScreen(
    currentRoute: String, navActions: NavActions, coroutineScope: CoroutineScope
) {
    val viewmodel: FavoritesViewmodel = rememberKoinInject()
    val listState = rememberLazyListState()
    val products by viewmodel.getProducts().collectAsState()


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerShape = DrawerDefaults.shape,
                drawerTonalElevation = DrawerDefaults.ModalDrawerElevation,
                drawerContainerColor = DrawerDefaults.containerColor,
                drawerContentColor = DrawerDefaults.containerColor,
                content = {
                    Drawer(
                        productsFlow = viewmodel.getProducts(),
                        currentRoute = currentRoute,
                        navActions = navActions
                    ) {

                    }
                })
        },
    ) {


        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBar("My Favorites") {
                    coroutineScope.launch {
                        withContext(Dispatchers.Main) {
                            drawerState.open()
                        }
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = listState
                    ) {
                        items(products, key = { it.id }) {
                            if (it.addedToFavorites)
                                FavoritesScreenProductCard(
                                    product = it
                                ) {
                                    viewmodel.removeFromFavorites(it)
                                }
                        }
                    }

                }


            }
        }

    }
}

@Composable
fun FavoritesScreenProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .animateContentSize(),
    ) {
        Productcard(
            product = product, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onFavoriteButtonClicked = onClick
        )


    }


}