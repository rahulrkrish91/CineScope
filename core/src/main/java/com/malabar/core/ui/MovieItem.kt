package com.malabar.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.malabar.core.R


@Composable
fun MovieItem(
    image: String,
    title: String,
    onClick: () -> Unit,
    height: Dp,
    width: Dp
) {
    OutlinedCard(
        onClick = { onClick() },
        modifier = Modifier.padding(5.dp)
    ) {
        Box(
            modifier = Modifier.width(width)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "Anime Image",
                modifier = Modifier
                    .height(height)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.no_image),
                placeholder = painterResource(R.drawable.no_image)
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

@Preview(showSystemUi = true)
@Composable
fun AnimeCardPreview() {
    MovieItem(
        image = "data:image/jpeg;base64,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",
        title = "Movie Title",
        onClick = {},
        height = 200.dp,
        width = 128.dp
    )
}