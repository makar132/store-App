package com.example.newmobileapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Menu
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newmobileapp.domain.CartProduct
import com.example.newmobileapp.domain.Product
import com.example.newmobileapp.presentation.ui.appbar.TopBar
import com.example.newmobileapp.presentation.ui.components.Productcard
import com.example.newmobileapp.presentation.ui.drawer.Drawer
import com.example.newmobileapp.presentation.viewmodels.HomeViewmodel
import com.example.newmobileapp.util.NavActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.rememberKoinInject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    currentRoute: String, navActions: NavActions, coroutineScope: CoroutineScope
) {
    val viewmodel: HomeViewmodel = rememberKoinInject()
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val products by viewmodel.getProductList().collectAsState()
    val categories by viewmodel.categories.collectAsState()
    val activeCategories = remember { mutableStateListOf<String>() }

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
                        productsFlow = viewmodel.getProductList(),
                        currentRoute = currentRoute,
                        navActions = navActions
                    ) {

                    }
                })
        },
    ) {


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {

                /*              Row(
                                  modifier = Modifier
                                      .fillMaxWidth()
                                      .padding(8.dp),
                                  horizontalArrangement = Arrangement.Start,
                                  verticalAlignment = Alignment.CenterVertically
                              ) {
                                  IconButton(modifier = Modifier.padding(8.dp), onClick = {
                                      coroutineScope.launch {
                                          withContext(Dispatchers.Main) {
                                              drawerState.open()
                                          }
                                      }

                                  }) {
                                      Icon(imageVector = Icons.Outlined.Menu, contentDescription = null)
                                  }
                                  Text(
                                      modifier = Modifier.padding(start = 36.dp), text = "Home",
                                      style = MaterialTheme.typography.titleLarge
                                  )

                              }
              */

            },

            ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBar {
                    coroutineScope.launch {
                        withContext(Dispatchers.Main) {
                            drawerState.open()
                        }
                    }
                }
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
                                text = "All categories", color = MaterialTheme.colorScheme.onSurface
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
                                    containerColor = if (!activeCategories.contains(category)) MaterialTheme.colorScheme.surface
                                    else MaterialTheme.colorScheme.outline
                                ), modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = category, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = listState
                    ) {
                        items(products, key = { it.id }) {
                            if (activeCategories.contains(it.category) or activeCategories.isEmpty()) HomeScreenProductcard(
                                product = it,
                                onAddClick = fun() {
                                    viewmodel.addToCart(CartProduct(it.id))
                                },
                                onRemoveClick = fun() {
                                    viewmodel.removeFromCart(CartProduct(it.id))
                                },
                                onFavoritesClick = fun(){
                                    viewmodel.changeProductFavoriteState(it.id)
                                }
                            )
                        }
                    }

                }


            }
        }

    }
}

@Composable
fun HomeScreenProductcard(product: Product, onAddClick: () -> Unit, onRemoveClick: () -> Unit, onFavoritesClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Productcard(product = product, modifier = Modifier.fillMaxWidth(), onFavoriteButtonClicked = onFavoritesClick)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            AnimatedContent(
                targetState = if (product.addedToCart) Triple(
                    Icons.Default.Clear, "remove from cart", onRemoveClick
                )
                else Triple(Icons.Default.Add, "add to cart", onAddClick),
                label = "cart options button",
                contentAlignment = Alignment.CenterStart
            ) {
                OutlinedButton(
                    onClick = it.third, modifier = Modifier
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
                                imageVector = it.first,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )

                        }
                        Text(text = it.second)

                    }

                }
            }

        }

    }


}