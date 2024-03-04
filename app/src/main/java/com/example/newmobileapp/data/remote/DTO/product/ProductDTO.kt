package com.example.newmobileapp.data.remote.DTO.product

import com.example.newmobileapp.data.remote.DTO.product.Rating

data class ProductDTO (
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)
