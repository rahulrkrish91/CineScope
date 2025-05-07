package com.malabar.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.malabar.core.R

@Composable
fun AsyncImageWithPlaceholder(
    model: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderResId: Int = R.drawable.placeholder, // Default placeholder
    errorResId: Int = R.drawable.no_image
) {

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = model)
            .apply(block = {
                CircularProgressIndicator()
            }).build()
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
        AsyncImage(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
            placeholder = painterResource(placeholderResId),
            error = painterResource(errorResId)
        )
    }
}