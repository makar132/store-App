package com.example.newmobileapp.di

import android.app.Application
import android.content.Context
import com.example.newmobileapp.data.remote.API.CartApi
import com.example.newmobileapp.data.remote.API.categoryApi
import com.example.newmobileapp.data.remote.API.productsApi
import com.example.newmobileapp.data.remote.Base_Url
import com.example.newmobileapp.data.repository.RemoteRepoImplementaion
import com.example.newmobileapp.data.repository.RemoteRepo
import com.example.newmobileapp.presentation.viewmodels.CartViewmodel
import com.example.newmobileapp.presentation.viewmodels.CoreViewmodel
import com.example.newmobileapp.presentation.viewmodels.FavoritesViewmodel
import com.example.newmobileapp.presentation.viewmodels.HomeViewmodel
import com.example.newmobileapp.presentation.viewmodels.NetworkStateViewModel
import com.example.newmobileapp.util.NetworkUtils
import org.koin.android.ext.koin.androidContext
import org.koin.compose.LocalKoinApplication
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModules = module {

    single {
        Retrofit.Builder().baseUrl(Base_Url).addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(productsApi::class.java) }
    single { get<Retrofit>().create(categoryApi::class.java) }
    single { get<Retrofit>().create(CartApi::class.java) }

    single {
        RemoteRepoImplementaion(
            productsApi = get(), categoryApi = get(), cartApi = get()
        )
    } bind RemoteRepo::class


    single { CoreViewmodel() }
    single { HomeViewmodel() }
    single { CartViewmodel() }
    single { FavoritesViewmodel() }
    single { NetworkStateViewModel() }
    scope<HomeViewmodel> {
        factory { get<Context>() } // Provides activity context for MyActivity scope
    }
    single { NetworkUtils(get()) }

}


