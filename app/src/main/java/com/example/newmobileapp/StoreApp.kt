package com.example.newmobileapp

import android.app.Application
import com.example.newmobileapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StoreApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StoreApp)
            appModules
        }
    }
}