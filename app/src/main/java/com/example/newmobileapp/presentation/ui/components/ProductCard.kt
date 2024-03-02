package com.example.newmobileapp.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.newmobileapp.R
import com.example.newmobileapp.domain.product


@Composable
fun Productcard(product: product, modifier: Modifier) {

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .padding(8.dp)
            .animateContentSize()
    ) {
        Row {
            Column(modifier = Modifier.weight(0.3f)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Product Image"
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = product.category,
                    style = typography.titleSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.title,
                    style = typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    overflow = TextOverflow.Clip,
                    style = typography.titleLarge
                )
                Row(

                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = product.price)
                }


                // Optionally display additional information (category, flags)
            }
        }
    }


}