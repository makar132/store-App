package com.example.newmobileapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmobileapp.data.mappers.toProduct
import com.example.newmobileapp.data.remote.API.productsApi
import com.example.newmobileapp.data.remote.Base_Url
import com.example.newmobileapp.data.repository.RemoteRepo
import com.example.newmobileapp.data.repository.RemoteRepoImplementaion
import com.example.newmobileapp.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HomeScreenViewmodel(): ViewModel(), KoinComponent {

    private val _products:MutableStateFlow< MutableList<Product>> = MutableStateFlow(mutableListOf())
    val products=_products.asStateFlow()
    val remoterepo : RemoteRepoImplementaion by inject()
    init{

        _products.value.add(
            Product(
            id = 2, title = "first", category ="a"
            )
        )

        _products.value.add(Product(
            id = 3, title = "second", category ="b"
        ))



        viewModelScope.launch{
           _products.value=remoterepo.getProducts().toMutableList()
        }

}

}