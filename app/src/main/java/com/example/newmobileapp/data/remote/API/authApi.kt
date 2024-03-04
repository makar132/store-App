package com.example.newmobileapp.data.remote.API

import com.example.newmobileapp.data.remote.DTO.auth.LoginRequest
import com.example.newmobileapp.data.remote.DTO.auth.LoginResponse
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface authApi
{
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}