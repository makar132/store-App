package com.example.newmobileapp.data.mappers

import com.example.newmobileapp.data.remote.DTO.product.ProductDTO
import com.example.newmobileapp.domain.Product

fun ProductDTO.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = rating
    )
}