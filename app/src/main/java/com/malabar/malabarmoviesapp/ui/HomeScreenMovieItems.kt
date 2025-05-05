package com.malabar.malabarmoviesapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500
import com.malabar.core.data.MovieItem
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.item.MovieItemScreen

@Composable
fun HomeScreenMovieItems(
    rowTitle: String,
    items: List<MovieItem>,
    navController: NavController
) {

    Column {
        Text(
            text = rowTitle,
            modifier = Modifier.padding(5.dp),
            fontWeight = FontWeight.Bold
        )

        LazyRow {
            items(items) { movie ->
                MovieItemScreen(
                    image = "$MOVIE_IMAGE_W_500${movie.poster_path}",
                    title = movie.title,
                    onClick = {
                        navController.navigate(
                            Screens.Details.createRoute(movie.id)
                        )
                    },
                    height = 200.dp
                )
            }
        }
    }
}