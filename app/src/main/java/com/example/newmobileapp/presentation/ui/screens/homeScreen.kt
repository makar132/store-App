package com.example.newmobileapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
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
import androidx.compose.ui.draw.clip
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
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Column(modifier = Modifier.fillMaxWidth(),verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {

                                        Text(text = "Network error tap to retry")
//                                        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                                            IconButton(onClick = { viewmodel.refresh() }) {
                                                Icon(
                                                    imageVector = Icons.Default.Refresh,
                                                    contentDescription = "Refresh"
                                                )
                                            }
  //                                      }

                                    }

                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .horizontalScroll(
                                                scrollState
                                            )
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                activeCategories.clear()

                                            }, colors = ButtonDefaults.buttonColors(
                                                containerColor = if (!activeCategories.isEmpty()) MaterialTheme.colorScheme.surface
                                                else MaterialTheme.colorScheme.outline
                                            ), modifier = Modifier.padding(10.dp)
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
                                                }, colors = ButtonDefaults.buttonColors(
                                                    containerColor = if (!activeCategories.contains(
                                                            category
                                                        )
                                                    ) MaterialTheme.colorScheme.surface
                                                    else MaterialTheme.colorScheme.outline
                                                ), modifier = Modifier.padding(10.dp)
                                            ) {
                                                Text(
                                                    text = category,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }
                                }

                                LazyColumn(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {

                                    items(products.filter { product ->
                                        activeCategories.contains(
                                            product.category
                                        ) or activeCategories.isEmpty()
                                    }, key = { product -> product.id }) { product ->
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
            .height(250.dp)
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Productcard(
            product = product,
            modifier = Modifier.fillMaxWidth(),
            onFavoriteButtonClicked = onFavoritesClick
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {

            val controlSpecs: Triple<ImageVector, String, () -> Unit> =
                if (product.addedToCart) Triple(
                    Icons.Default.Clear, "remove from cart", onRemoveClick
                )
                else Triple(Icons.Default.Add, "add to cart", onAddClick)

            OutlinedButton(
                onClick = controlSpecs.third, modifier = Modifier
                    .wrapContentSize()
                    .animateContentSize()
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(4.dp),
                    ) {
                        Icon(
                            imageVector = controlSpecs.first,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                    }
                    Text(text = controlSpecs.second)

                }

            }


        }

    }


}