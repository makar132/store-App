package com.example.newmobileapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
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
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newmobileapp.presentation.ui.components.Productcard
import com.example.newmobileapp.presentation.ui.drawer.LeftDrawer
import com.example.newmobileapp.presentation.viewmodels.HomeScreenViewmodel
import com.example.newmobileapp.util.NavActions
import com.example.newmobileapp.util.NavRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.List

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
) {
    val viewmodel: HomeScreenViewmodel = viewModel()
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val products by viewmodel.products.collectAsState()
    val categories by viewmodel.categories.collectAsState()
    val activeCategories = remember { mutableStateListOf<String>() }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = DrawerDefaults.shape,
                drawerTonalElevation = DrawerDefaults.ModalDrawerElevation,
                drawerContainerColor = DrawerDefaults.containerColor,
                drawerContentColor = DrawerDefaults.containerColor,
                content = {
                    LeftDrawer(
                        products = products,
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
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start
                    , verticalAlignment =  Alignment.CenterVertically
                ) {
                    IconButton(modifier= Modifier.padding(8.dp),onClick = {
                        coroutineScope.launch {
                            withContext(Dispatchers.Main) {
                                drawerState.open()
                            }
                        }

                    }) {
                        Icon(imageVector = Icons.Outlined.Menu, contentDescription = null)
                    }
                        Text(modifier = Modifier.padding(start = 36.dp),text = "Home",
                            style = MaterialTheme.typography.titleLarge)

                }


            },

            ) {
            Column(
                modifier = Modifier.padding(top=40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
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

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                if (!activeCategories.isEmpty()) MaterialTheme.colorScheme.surface
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(
                                text = "All categories",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        for (category in categories) {
                            OutlinedButton(
                                onClick = {
                                    if (activeCategories.contains(category))
                                        activeCategories.remove(category)
                                    else activeCategories.add(category)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                    if (!activeCategories.contains(category)) MaterialTheme.colorScheme.surface
                                    else MaterialTheme.colorScheme.surfaceVariant
                                ),
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = category, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                Box(modifier = Modifier.weight(9f))
                {
                    LazyColumn(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = listState
                    ) {
                        items(products, key = { it.id }) {
                            if (activeCategories.contains(it.category) or activeCategories.isEmpty())
                                Productcard(product = it, modifier = Modifier.fillMaxWidth())
                        }
                    }

                }


            }
        }

    }
}