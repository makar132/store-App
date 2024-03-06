package com.example.newmobileapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmobileapp.data.repository.RemoteRepoImplementaion
import com.example.newmobileapp.domain.CartProduct
import com.example.newmobileapp.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewmodel() : ViewModel(), KoinComponent {

    private val _products: MutableStateFlow<MutableList<Product>> =
        MutableStateFlow(mutableListOf())
    private val products = _products.asStateFlow()
    private val _categories: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val categories = _categories.asStateFlow()

    private val remoterepo: RemoteRepoImplementaion by inject()

    private val cartViewmodel: CartViewmodel by inject()

    private val coreViewmodel: CoreViewmodel by inject()

    init {
        getOnlineProducts()
        getCategories()

    }

    private fun getOnlineProducts() {
        viewModelScope.launch {
            _products.value = remoterepo.getProducts().toMutableList()
        }
    }

    fun getProductList(): StateFlow<List<Product>> {
        return products
    }

    private fun getCategories() {
        viewModelScope.launch {
            _categories.value = remoterepo.getCategories()
        }
    }

    fun toggleAddedToCartFlag(productId: Int) {
        _products.value = _products.value.toMutableList().apply {
            this.replaceAll { product ->
                if (product.id == productId) {
                    product.copy(addedToCart = !product.addedToCart)
                } else {
                    product
                }
            }
        }
    }

    fun toggleFavoriteFlag(productId: Int) {
        _products.value = _products.value.toMutableList().apply {
            this.replaceAll { it ->
                if (it.id == productId) {
                    it.copy(addedToFavorites = !it.addedToFavorites)
                } else {
                    it
                }
            }
        }
    }

    fun changeProductFavoriteState(productId: Int) {
        coreViewmodel.changeProductFavoriteState(productId)
    }

    fun addToCart(cartProduct: CartProduct) {
        coreViewmodel.addToCart(cartProduct)

    }

    fun removeFromCart(cartProduct: CartProduct) {
        coreViewmodel.removeFromCart(cartProduct)
    }
}