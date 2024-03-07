package com.example.newmobileapp.data.repository

import com.example.newmobileapp.data.mappers.toProduct
import com.example.newmobileapp.data.remote.API.CartApi
import com.example.newmobileapp.data.remote.API.categoryApi
import com.example.newmobileapp.data.remote.API.productsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class RemoteRepoImplementaion(
    private val productsApi: productsApi,
    val categoryApi: categoryApi,
    val cartApi: CartApi
) : RemoteRepo {
    override suspend fun getProducts() =
        withContext(Dispatchers.IO) {
            productsApi.getProducts().map { it.toProduct() }
        }

    suspend fun getOnlineProducts(
        loadingState: MutableStateFlow<Boolean>,
        errorState: MutableStateFlow<Boolean>
    ) =
        withContext(Dispatchers.IO) {
            loadingState.value = true
            try {
                return@withContext getProducts().apply {
                    errorState.value = false
                }

            } catch (e: Exception) {

                errorState.value = true
                return@withContext emptyList()
            }

        }.apply {
            loadingState.value = false
        }


    override suspend fun getCategories() =
        withContext(Dispatchers.IO) {

            try {
                return@withContext categoryApi.getCategories()
            } catch (e: Exception) {
                return@withContext emptyList()
            }
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