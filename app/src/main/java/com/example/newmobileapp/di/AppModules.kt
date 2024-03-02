package com.example.newmobileapp.di
import com.example.newmobileapp.presentation.viewmodels.HomeScreenViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
viewModel { HomeScreenViewmodel() }
}


