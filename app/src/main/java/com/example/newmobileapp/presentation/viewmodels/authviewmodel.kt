package com.example.newmobileapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.newmobileapp.data.remote.API.authApi
import com.example.newmobileapp.data.remote.DTO.auth.LoginRequest
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class authviewmodel:ViewModel(),KoinComponent {
    var token="no login"
    fun login(username: String, password: String):String {

        val authService:authApi by inject()

        val loginRequest = LoginRequest(username = username.ifEmpty { null } , password= password.ifEmpty { null } )
        runBlocking {

            token = try {

                val call = authService.login(loginRequest)
                Log.e("ass","login")
                    call.body().toString()
            } catch (e: Exception) {
                ("Error during login: $e")
                // Handle the error appropriately
            }
        }

    return token
    }

}
