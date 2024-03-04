package com.example.newmobileapp.data.repository

import com.example.newmobileapp.data.mappers.toProduct
import com.example.newmobileapp.data.remote.API.productsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteRepoImplementaion(private val api:productsApi):RemoteRepo {
    override suspend fun getProducts()=
        withContext(Dispatchers.IO){
            api.getProducts().map{it.toProduct()}
        }

}