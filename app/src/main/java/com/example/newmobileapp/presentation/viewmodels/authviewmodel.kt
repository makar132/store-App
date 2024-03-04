package com.example.newmobileapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmobileapp.data.remote.API.authApi
import com.example.newmobileapp.data.remote.DTO.auth.LoginRequest
import com.example.newmobileapp.data.remote.DTO.auth.LoginResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class authviewmodel:ViewModel(),KoinComponent {
    fun login(username: String, password: String):String {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON conversion
            .build()

        val authService = retrofit.create(authApi::class.java)

        val loginRequest = LoginRequest(username = username.ifEmpty { null } , password= password.ifEmpty { null } )
        var Token="xxxx"

        runBlocking {

            Token = try {

                val call = authService.login(loginRequest)
                Log.e("ass","login")
                if(call.isSuccessful){
                    call.body().toString()
                } else {
                    "errrrrr"
                }

            } catch (e: Exception) {
                ("Error during login: $e")
                // Handle the error appropriately
            }
        }

    return Token
    }

}
