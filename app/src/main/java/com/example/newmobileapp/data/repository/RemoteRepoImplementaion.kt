package com.example.newmobileapp.data.repository

import com.example.newmobileapp.data.mappers.toProduct
import com.example.newmobileapp.data.remote.API.cartApi
import com.example.newmobileapp.data.remote.API.categoryApi
import com.example.newmobileapp.data.remote.API.productsApi
import com.example.newmobileapp.domain.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteRepoImplementaion(
    private val productsApi: productsApi,
    val categoryApi: categoryApi,
    val cartApi: cartApi
) : RemoteRepo {
    override suspend fun getProducts() =
        withContext(Dispatchers.IO) {
            productsApi.getProducts().map { it.toProduct() }
        }

    override suspend fun getCategories() =
        withContext(Dispatchers.IO) {
            categoryApi.getCategories()
        }

    override suspend fun getCarts() =
        withContext(Dispatchers.IO) {
            cartApi.getCarts()
        }

    override suspend fun getSingleCart(id: Int) =
        withContext(Dispatchers.IO) {
        cartApi.getSingleCart(id)
    }


}