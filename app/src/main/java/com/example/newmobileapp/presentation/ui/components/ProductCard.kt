package com.example.newmobileapp.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newmobileapp.domain.Product


@Composable
fun Productcard(
    product: Product, onFavoriteButtonClicked: () -> Unit, modifier: Modifier
) {
    var (expanded, setExtended) = remember { mutableStateOf(false) }

    Card(shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .fillMaxSize()
            .clickable {
                setExtended(!expanded)
            }
            .animateContentSize()) {
        Box(modifier = Modifier.fillMaxSize()) {

            Row {

                Box(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxSize()
                        .animateContentSize(),
                    contentAlignment = Alignment.TopStart

                ) {
                    SubcomposeAsyncImage(
                        modifier = modifier
                            .size(80.dp, 200.dp)
                            .padding(4.dp)
                            .animateContentSize(),
                        model = product.image,
                        contentDescription = null,
                        alignment = Alignment.CenterStart,
                        contentScale = ContentScale.FillBounds,
                        clipToBounds = false
                    ) {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            CircularProgressIndicator()
                        } else {
                            SubcomposeAsyncImageContent(modifier = Modifier.fillMaxSize())
                        }
                    }
                }



                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(7f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                            .animateContentSize(),
                        maxLines = if (expanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis,
                        text = product.title,
                        style = typography.titleMedium
                    )

                    Text(
                        text = product.price.toString() + " $",
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )



                    Text(
                        modifier = Modifier
                            .animateContentSize()
                            .fillMaxWidth(),
                        text = product.description,
                        maxLines = if (expanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis,
                        style = typography.titleSmall
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFD700)
                            )
                            Text(text = "${product.rating.rate}")
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color(0xFFFFD700)
                            )
                            Text(text = "${product.rating.count}")
                        }
                    }

                    // Optionally display additional information (category, flags)
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth()
                //  .padding(8.dp)
                , verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start
            ) {
                AnimatedContent(
                    targetState = if (product.addedToFavorites) Icons.Outlined.Favorite
                    else Icons.Outlined.FavoriteBorder, label = "favorite Icon"
                ) {
                    IconButton(
                        onClick = onFavoriteButtonClicked,
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

            }


        }

    }


}

