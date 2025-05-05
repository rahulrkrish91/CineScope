package com.malabar.malabarmoviesapp.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.malabar.malabarmoviesapp.ui.MovieDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieItemScreen(
    image: String,
    title: String,
    onClick: () -> Unit,
    height: Dp,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel()
) {

    OutlinedCard(
        onClick = { onClick() },
        modifier = Modifier.padding(5.dp)
    ) {
        Box(
            modifier = Modifier.width(128.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "Anime Image",
                modifier = Modifier
                    .height(height)
                    .width(128.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 0f,
                            endY = 200f
                        )
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.BottomStart),
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}