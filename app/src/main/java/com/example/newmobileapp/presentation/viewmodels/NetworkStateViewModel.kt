package com.example.newmobileapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmobileapp.util.NetworkConnectionState
import com.example.newmobileapp.util.NetworkUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkStateViewModel : ViewModel(), KoinComponent {

    private val networkUtils: NetworkUtils by inject()
    val networkState: StateFlow<NetworkConnectionState> = networkUtils.getNetworkState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = NetworkConnectionState.Available
    )

}