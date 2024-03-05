package com.example.newmobileapp.data.remote.API

import com.example.newmobileapp.data.remote.DTO.product.ProductDTO

import retrofit2.http.GET

interface productsApi {
    @GET("products")
    suspend fun getProducts():List<ProductDTO>
    @GET("products/categories")
    suspend fun getCategories():List<String>

}