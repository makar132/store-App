package com.example.newmobileapp.data.repository

import com.example.newmobileapp.domain.Cart
import com.example.newmobileapp.domain.Product

interface RemoteRepo {
    suspend fun getProducts(): List<Product>

    suspend fun getCategories(): List<String>

    suspend fun getCarts(): List<Cart>
    suspend fun getSingleCart(id: Int = 1): Cart

}