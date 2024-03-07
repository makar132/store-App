package com.example.newmobileapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.newmobileapp.domain.CartProduct
import com.example.newmobileapp.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CoreViewmodel : ViewModel(), KoinComponent {
    private val homeViewmodel: HomeViewmodel by inject()
    private val cartViewmodel: CartViewmodel by inject()
    fun addToCart(cartProduct: CartProduct) {
        cartViewmodel.addToCart(cartProduct)
    }

    fun addToCart(cart: MutableStateFlow<MutableList<CartProduct>>, cartProduct: CartProduct) {
        homeViewmodel.toggleAddedToCartFlag(cartProduct.productId)
        cart.value = cart.value.plus(cartProduct).toMutableList()
    }

    fun removeFromCart(cartProduct: CartProduct) {
        cartViewmodel.removeFromCart(cartProduct)
    }

    fun removeFromCart(cart: MutableStateFlow<MutableList<CartProduct>>, cartProduct: CartProduct) {
        homeViewmodel.toggleAddedToCartFlag(cartProduct.productId)
        cart.value = cart.value.minus(cartProduct).toMutableList()
    }

    fun changeProductFavoriteState(productId: Int) {
        homeViewmodel.toggleFavoriteFlag(productId)
    }

    fun getProductList(): StateFlow<List<Product>> {
        return homeViewmodel.getProductList()
    }

    companion object {

    }

}