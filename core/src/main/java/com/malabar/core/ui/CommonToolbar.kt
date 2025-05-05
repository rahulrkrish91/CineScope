package com.malabar.core.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonToolbar(
    onSearchClick: () -> Unit
) {
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    TopAppBar(
        title = {
            Text(
                text = "Home"
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            /*IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification"
                )
            }*/
        },
        scrollBehavior = scrollBehaviour
    )
}

@Preview(showSystemUi = true)
@Composable
fun CommonToolbarPreview() {
    CommonToolbar(
        onSearchClick = {}
    )
}