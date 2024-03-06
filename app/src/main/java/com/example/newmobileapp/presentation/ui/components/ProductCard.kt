package com.example.newmobileapp.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.newmobileapp.R
import com.example.newmobileapp.domain.Product
import kotlinx.coroutines.flow.StateFlow


@Composable
fun Productcard(
    product: Product,
    onFavoriteButtonClicked: () -> Unit,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .size(250.dp)
            .fillMaxSize()
            .animateContentSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedContent(
                    targetState = if (product.addedToFavorites) Icons.Outlined.Favorite
                    else Icons.Outlined.FavoriteBorder, label = "favorite Icon"
                ) {
                    FloatingActionButton(onClick = onFavoriteButtonClicked) {
                        Icon(imageVector = it, contentDescription = null)
                    }
                }

            }
            Row {
                Column(modifier = Modifier.weight(3f)) {


                    Box {
                        SubcomposeAsyncImage(
                            modifier = modifier.padding(8.dp, 16.dp),
                            model = product.image,
                            contentDescription = null,
                            alignment = Alignment.CenterStart,
                            clipToBounds = false
                        ) {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                CircularProgressIndicator()
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }

                }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(7f), verticalArrangement = Arrangement.Top
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        text = product.category,
                        style = typography.titleSmall
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = product.price.toString() + " $", color = Color.Blue)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(),
                        text = product.title,
                        style = typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f),
                        text = product.description,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = typography.titleSmall
                    )


                    // Optionally display additional information (category, flags)
                }

            }
        }

    }


}

