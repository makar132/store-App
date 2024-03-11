package com.example.newmobileapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.newmobileapp.domain.CartProduct
import com.example.newmobileapp.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CartViewmodel : ViewModel(), KoinComponent {


    private val _cart: MutableStateFlow<MutableList<CartProduct>> =
        MutableStateFlow(mutableListOf())
    private var cart = _cart.asStateFlow()

    private val coreViewmodel: CoreViewmodel by inject()

    init {

    }

    fun addToCart(cartProduct: CartProduct) {
        coreViewmodel.addToCart(_cart, cartProduct)
    }

    fun removeFromCart(cartProduct: CartProduct) {
        coreViewmodel.removeFromCart(_cart, cartProduct)
    }


    fun getCart(): StateFlow<MutableList<CartProduct>> {
        return cart
    }

    fun changeProductFavoriteState(productId: Int) {
        coreViewmodel.changeProductFavoriteState(productId)
    }

    fun getProducts(): StateFlow<List<Product>> {
        return coreViewmodel.getProductList()
    }


}