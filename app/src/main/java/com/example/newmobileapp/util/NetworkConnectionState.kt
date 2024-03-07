package com.example.newmobileapp.util

sealed interface NetworkConnectionState {
    object Available : NetworkConnectionState
    object Unavailable : NetworkConnectionState
    object Losing : NetworkConnectionState
    object Lost : NetworkConnectionState

    //   Available, Unavailable, Losing, Lost

}