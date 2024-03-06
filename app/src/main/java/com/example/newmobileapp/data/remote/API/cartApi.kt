package com.example.newmobileapp.data.remote.API

import com.example.newmobileapp.domain.Cart
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface cartApi {
    @GET("carts")
    suspend fun getCarts():List<Cart>
    @GET("carts/{id}")
    suspend fun getSingleCart(
        @Path("id") id: Int
    ):Cart

}