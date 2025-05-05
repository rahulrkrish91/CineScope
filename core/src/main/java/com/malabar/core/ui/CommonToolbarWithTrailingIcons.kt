package com.malabar.core.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.malabar.core.R

@Composable
fun CommonToolbarWithTrailingIcons(
    title: String,
    navController: NavController,
    onGalleryClick: () -> Unit = {},
    onVideoClick: () -> Unit = {},
    onReviewClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Arrow
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        // Title
        Text(
            text = title,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )

        // Right Icons
        Row {
            IconButton(onClick = onGalleryClick) {
                Icon(painter = painterResource(R.drawable.gallery), contentDescription = "Gallery")
            }
            IconButton(onClick = onVideoClick) {
                Icon(painter = painterResource(R.drawable.video), contentDescription = "Video")
            }
            IconButton(onClick = onReviewClick) {
                Icon(painter = painterResource(R.drawable.rating), contentDescription = "Rating")
            }
        }
    }
}