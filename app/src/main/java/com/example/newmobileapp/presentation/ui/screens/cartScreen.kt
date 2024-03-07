package com.example.newmobileapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newmobileapp.domain.Product
import com.example.newmobileapp.presentation.ui.appbar.TopBar
import com.example.newmobileapp.presentation.ui.components.Productcard
import com.example.newmobileapp.presentation.ui.utils.Drawer
import com.example.newmobileapp.presentation.viewmodels.CartViewmodel
import com.example.newmobileapp.util.NavActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope
) {
    val viewmodel: CartViewmodel = koinInject()
    val products by viewmodel.getProducts().collectAsState()
    val cart by viewmodel.getCart().collectAsState()


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = DrawerDefaults.shape,
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
                }
            )
        },
    ) {


        Scaffold(
            modifier = Modifier.fillMaxSize(),

            ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBar("My Cart") {
                    coroutineScope.launch {
                        withContext(Dispatchers.Main) {
                            drawerState.open()
                        }
                    }
                }
                Box()
                {
                    LazyColumn(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        items(cart, key = { it.productId }) {
                            CartScreenProductCard(product = products.find { product -> product.id == it.productId },
                                onClick = fun() {
                                    viewmodel.removeFromCart(it)
                                },
                                onFavoriteClick = fun() {
                                    viewmodel.changeProductFavoriteState(it.productId)
                                })
                        }
                    }

                }


            }
        }

    }
}

@Composable
fun CartScreenProductCard(
    product: Product?,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,

    ) {

    Box(
        modifier = Modifier
            .wrapContentSize()
            .animateContentSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        if (product != null) {
            Column {
                Row {
                    Productcard(
                        product = product,
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        onFavoriteButtonClicked = onFavoriteClick
                    )
                }


                Row {
                    OutlinedButton(
                        shape = RoundedCornerShape(8.dp),
                        onClick = onClick,
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(text = "remove from cart")
                    }
                }
            }
        }


    }


}
