package com.example.newmobileapp.di

import com.example.newmobileapp.data.remote.API.authApi
import com.example.newmobileapp.data.remote.API.cartApi
import com.example.newmobileapp.data.remote.API.categoryApi
import com.example.newmobileapp.data.remote.API.productsApi
import com.example.newmobileapp.data.remote.Base_Url
import com.example.newmobileapp.data.repository.RemoteRepoImplementaion
import com.example.newmobileapp.data.repository.RemoteRepo
import com.example.newmobileapp.presentation.viewmodels.CartViewmodel
import com.example.newmobileapp.presentation.viewmodels.CoreViewmodel
import com.example.newmobileapp.presentation.viewmodels.FavoritesViewmodel
import com.example.newmobileapp.presentation.viewmodels.HomeViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModules = module {
    single {
        Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(productsApi::class.java) }
    single { get<Retrofit>().create(categoryApi::class.java) }
    single { get<Retrofit>().create(cartApi::class.java) }


    single {
        get<Retrofit>().create(authApi::class.java)
    }

    single {
        RemoteRepoImplementaion(
            productsApi = get(),
            categoryApi = get(),
            cartApi= get()
        )
    } bind RemoteRepo::class

    single { HomeViewmodel() }
    single { CartViewmodel() }
    single { CoreViewmodel() }
    single { FavoritesViewmodel() }


}


