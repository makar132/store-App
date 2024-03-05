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

    private val _products:MutableStateFlow< List<Product>> = MutableStateFlow(listOf())
    val products=_products.asStateFlow()
    private val _categories:MutableStateFlow< List<String>> = MutableStateFlow(listOf())
    val categories=_categories.asStateFlow()

    val remoterepo : RemoteRepoImplementaion by inject()
    init{
        getProducts()
        getCategories()

    }

    fun getProducts(){
        viewModelScope.launch{
            _products.value=remoterepo.getProducts()
        }
    }
    fun getCategories(){
        viewModelScope.launch{
            _categories.value=remoterepo.getCategories()
        }
    }

}