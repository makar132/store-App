package com.example.newmobileapp.di
import com.example.newmobileapp.data.remote.API.productsApi
import com.example.newmobileapp.data.remote.Base_Url
import com.example.newmobileapp.data.repository.RemoteRepoImplementaion
import com.example.newmobileapp.data.repository.RemoteRepo
import com.example.newmobileapp.presentation.viewmodels.CartViewmodel
import com.example.newmobileapp.presentation.viewmodels.HomeScreenViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModules = module {
single <productsApi>{
    Retrofit.Builder()
        .baseUrl(Base_Url)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(productsApi::class.java)
        }
    single {  RemoteRepoImplementaion(api = get()) } bind RemoteRepo::class

    //viewModel { HomeScreenViewmodel() }
    //viewModel { CartViewmodel() }

}


