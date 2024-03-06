package com.example.newmobileapp.presentation.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newmobileapp.R
import com.example.newmobileapp.domain.Product
import com.example.newmobileapp.util.NavActions
import com.example.newmobileapp.util.NavRoutes
import kotlinx.coroutines.flow.StateFlow


@Composable
fun Drawer(
    productsFlow: StateFlow<List<Product>>,
    currentRoute: String,
    navActions: NavActions,
    closeLeftDrawer: () -> Unit
) {
    val products by productsFlow.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val itemsInCart = products.filter { it.addedToCart }
            val favoriteItems = products.filter { it.addedToFavorites }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationDrawerItem(
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home icon") },
                label = {
                        Text(text = "Home")
                },
                selected = currentRoute == NavRoutes.HOME,
                onClick = {
                    navActions.navigateToHome()
                    closeLeftDrawer()
                },
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                badge = { Text(text = products.size.toString()) },
                modifier = Modifier.padding(end = 16.dp, bottom = 12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart icon"
                    )
                },
                label = { Text(text = "cart") },
                selected = currentRoute == NavRoutes.CART,
                onClick = {
                    navActions.navigateToCart()
                    closeLeftDrawer()
                },
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                badge = { Text(text =itemsInCart.size.toString()) },
                modifier = Modifier.padding(end = 16.dp, top = 12.dp, bottom = 12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite products icon"
                    )
                },
                label = { Text(text = "favorites") },
                selected = currentRoute == NavRoutes.FAVORITES,
                onClick = {
                    navActions.navigateToFavorites()
                    closeLeftDrawer()
                },
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                badge = { Text(text = favoriteItems.size.toString()) },
                modifier = Modifier.padding(end = 16.dp, top = 12.dp, bottom = 12.dp)
            )
        }

    }
}