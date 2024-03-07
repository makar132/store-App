package com.example.newmobileapp.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun NetworkStatusView(networkState: Boolean) {
    val color by animateColorAsState(
        targetValue = if (networkState) Color(0xff24d65f) else Color(
            0xffff6363
        ), animationSpec = tween(250), label = ""
    )
    // Create a MutableTransitionState<Boolean> for the AnimatedVisibility.
    val state = remember {
        mutableStateOf(true)
    }
    LaunchedEffect(networkState != state.value) {
        if (networkState) delay(3000)
        state.value = networkState
    }
    AnimatedVisibility(
        visible = !state.value,
        enter = scaleIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it },
    ) {
        Card(
            modifier = Modifier.padding(vertical = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(
                        color = color
                    )
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (networkState) {
                        true -> "Connection restored"
                        false -> "No internet connection"
                    },
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

    }

}

