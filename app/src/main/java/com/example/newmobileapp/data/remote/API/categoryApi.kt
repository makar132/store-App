package com.example.newmobileapp.data.remote.API

import retrofit2.http.GET

interface categoryApi {
    @GET("products/categories")
    suspend fun getCategories():List<String>
}