package com.example.newmobileapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.newmobileapp.domain.product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class CartViewmodel : ViewModel(), KoinComponent {

    private val _products: MutableStateFlow<MutableList<product>> = MutableStateFlow(mutableListOf())
    val products=_products.asStateFlow()
    init{
        _products.value.add(
            product(
                id = 4, title = "first", category ="c"
            )
        )

        _products.value.add(
            product(
            id = 5, title = "second", category ="d"
        )
        )

    }

}