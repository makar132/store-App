package com.example.newmobileapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.newmobileapp.domain.CartProduct
import com.example.newmobileapp.domain.Product
import com.example.newmobileapp.presentation.ui.appbar.TopBar
import com.example.newmobileapp.presentation.ui.components.Productcard
import com.example.newmobileapp.presentation.ui.utils.Drawer
import com.example.newmobileapp.presentation.ui.utils.LoadingUtil
import com.example.newmobileapp.presentation.viewmodels.HomeViewmodel
import com.example.newmobileapp.util.NavActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.rememberKoinInject

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedContentLambdaTargetStateParameter")
@Composable
fun HomeScreen(
    currentRoute: String, navActions: NavActions, coroutineScope: CoroutineScope
) {
    val viewmodel: HomeViewmodel = rememberKoinInject()
    val scrollState = rememberScrollState()
    val products by viewmodel.getProductList().collectAsState()
    val categories by viewmodel.categories.collectAsState()
    val activeCategories = remember { mutableStateListOf<String>() }
    val loading by viewmodel.loading.collectAsState()
    val loadingError by viewmodel.error.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)




    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerShape = DrawerDefaults.shape,
                drawerTonalElevation = DrawerDefaults.ModalDrawerElevation,
                drawerContainerColor = MaterialTheme.colorScheme.primary,
                drawerContentColor = MaterialTheme.colorScheme.onPrimary,
                content = {
                    Drawer(
                        productsFlow = viewmodel.getProductList(),
                        currentRoute = currentRoute,
                        navActions = navActions
                    ) {

                    }
                })
        },
    ) {
        AnimatedContent(targetState = loading, label = "") { isLoadingData ->
            if (isLoadingData) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LoadingUtil().LoadingIndicator()
                }
            } else {


                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TopBar("My Store") {
                            coroutineScope.launch {
                                withContext(Dispatchers.Main) {
                                    drawerState.open()
                                }
                            }
                        }
                        AnimatedContent(targetState = loadingError, label = "") {
                            if (it) {

                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(text = "Oops, there was a problem loading. Tap to retry ")
                                    IconButton(onClick = { viewmodel.refresh() }) {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = "Refresh"
                                        )
                                    }
                                }


                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .horizontalScroll(
                                                scrollState
                                            ), horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                activeCategories.clear()

                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (!activeCategories.isEmpty()) MaterialTheme.colorScheme.surface
                                                else MaterialTheme.colorScheme.outline
                                            ), //modifier = Modifier.padding(10.dp)
                                        ) {
                                            Text(
                                                text = "All categories",
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        for (category in categories) {
                                            OutlinedButton(
                                                onClick = {
                                                    if (activeCategories.contains(category)) activeCategories.remove(
                                                        category
                                                    )
                                                    else activeCategories.add(category)
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = if (!activeCategories.contains(
                                                            category
                                                        )
                                                    ) MaterialTheme.colorScheme.surface
                                                    else MaterialTheme.colorScheme.outline
                                                ), //modifier = Modifier.padding(10.dp)
                                            ) {
                                                Text(
                                                    text = category,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }

                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {


                                        items(products.filter { product ->
                                            activeCategories.contains(
                                                product.category
                                            ) or activeCategories.isEmpty()
                                        }, key = { product -> product.id }) { product ->
                                            Row(
                                                modifier = Modifier.animateItemPlacement(
                                                    tween(durationMillis = 250)
                                                )
                                            ) {
                                                HomeScreenProductCard(product = product,
                                                    onAddClick = fun() {
                                                        viewmodel.addToCart(CartProduct(product.id))
                                                    },
                                                    onRemoveClick = fun() {
                                                        viewmodel.removeFromCart(CartProduct(product.id))
                                                    },
                                                    onFavoritesClick = fun() {
                                                        viewmodel.changeProductFavoriteState(product.id)
                                                    })
                                            }
                                            Spacer(modifier = Modifier.height(2.dp))
                                        }


                                    }


                                }


                            }

                        }

                    }
                }

            }
        }

    }
}

@Composable
fun HomeScreenProductCard(
    product: Product,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .animateContentSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Productcard(
            product = product,
            modifier = Modifier.fillMaxSize(),
            onFavoriteButtonClicked = onFavoritesClick
        )

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End

        ) {

            val controlSpecs: Triple<ImageVector, String, () -> Unit> =
                if (product.addedToCart) Triple(
                    Icons.Default.Clear, "remove from cart", onRemoveClick
                )
                else Triple(Icons.Default.Add, "add to cart", onAddClick)


            ElevatedButton(
                onClick = controlSpecs.third,
                modifier = Modifier
                    .wrapContentSize()
                    .height(30.dp)
                    .animateContentSize(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 4.dp),
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxHeight(0.6f),
                    imageVector = controlSpecs.first,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text(modifier = Modifier.padding(horizontal = 8.dp), text = controlSpecs.second)


            }


        }

    }


}