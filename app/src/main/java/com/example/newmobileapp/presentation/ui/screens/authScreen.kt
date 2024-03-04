package com.example.newmobileapp.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newmobileapp.presentation.viewmodels.authviewmodel

@Composable
fun AuthScreen(){
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (token,setToken) = remember { mutableStateOf("empty token") }
    val viewmodel:authviewmodel = viewModel()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(value =username , onValueChange ={setUsername(it)} )
        OutlinedTextField(value =password , onValueChange ={setPassword(it)} )
        OutlinedButton(onClick = {
            setToken(viewmodel.login(username,password))
        }) {
Text(text = "login")
        }
        Text(text =token)
    }


}