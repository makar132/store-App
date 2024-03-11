package com.example.newmobileapp.domain

data class Cart(
    val date: String,
    val id: Int,
    val products: List<CartProduct>,
    val userId: Int
)