package com.example.newmobileapp.data.repository

import com.example.newmobileapp.domain.Product

interface RemoteRepo {
    suspend fun getProducts():List<Product>
    suspend fun getCategories():List<String>
}