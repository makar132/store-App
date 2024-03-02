package com.example.newmobileapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newmobileapp.presentation.ui.components.Productcard
import com.example.newmobileapp.presentation.viewmodels.CartViewmodel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (){
    val viewmodel:CartViewmodel= viewModel()
    val listState = rememberLazyListState()
   val products by viewmodel.products.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ){
                items(products,key = {it.id}){
                    Productcard(product = it, modifier = Modifier.fillMaxWidth())
                }
            }
        }

    }
}