package com.example.newmobileapp.presentation.ui.utils

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties


class LoadingUtil {
    @Composable
    private fun ProgressIndicatorLoading(
        progressIndicatorColor: Color = MaterialTheme.colorScheme.secondary,
        progressIndicatorSize: Dp = 40.dp,
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "")

        val angle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 600 // animation duration
                }
            ), label = ""
        )

        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier
                .size(progressIndicatorSize)
                .rotate(angle)
                .border(
                    6.dp,
                    brush = Brush.sweepGradient(
                        listOf(
                            Color.White, // add background color first
                            progressIndicatorColor.copy(alpha = 0.3f),
                            progressIndicatorColor
                        )
                    ),
                    shape = CircleShape
                ),
            color = Color.White, // Set background color
            strokeWidth = 1.dp,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LoadingDialog(
        cornerRadius: Dp = 16.dp,
    ) {
        AlertDialog(
            onDismissRequest = { },
            properties = DialogProperties(usePlatformDefaultWidth = false),

            ) {
            Column(
                modifier = Modifier
                    .padding(start = 42.dp, end = 42.dp) // margin
                    .background(color = Color.White, shape = RoundedCornerShape(cornerRadius))
                    .padding(top = 36.dp, bottom = 36.dp), // inner padding
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                ProgressIndicatorLoading()
                Spacer(modifier = Modifier.height(25.dp))
                Text(text = "loading ...")
            }
        }
    }

    @Composable
    fun LoadingIndicator() {
        LoadingDialog()
    }

}
