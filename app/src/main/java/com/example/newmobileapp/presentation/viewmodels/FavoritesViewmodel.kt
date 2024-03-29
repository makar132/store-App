package com.example.newmobileapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.newmobileapp.domain.Product
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavoritesViewmodel : ViewModel(), KoinComponent {


    private val coreViewmodel: CoreViewmodel by inject()

    fun getProducts(): StateFlow<List<Product>> {
        return coreViewmodel.getProductList()
    }

    fun removeFromFavorites(product: Product) {
        coreViewmodel.changeProductFavoriteState(product.id)
    }
}