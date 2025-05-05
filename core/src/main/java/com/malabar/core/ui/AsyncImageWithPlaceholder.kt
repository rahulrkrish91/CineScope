package com.malabar.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
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

    var showImageError by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        val request = ImageRequest.Builder(LocalPlatformContext.current)
            .data(model)
            .build()

        if (showImageError) {
            Image(
                painter = painterResource(id = errorResId),
                contentDescription = "Image load error",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Gray)

            )
        } else {
            AsyncImage(
                model = request,
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.matchParentSize(),
                onSuccess = { _ ->
                    showImageError = false
                },
                onError = { _ ->
                    showImageError = true
                },
                placeholder = painterResource(id = placeholderResId)
            )
        }

    }
}