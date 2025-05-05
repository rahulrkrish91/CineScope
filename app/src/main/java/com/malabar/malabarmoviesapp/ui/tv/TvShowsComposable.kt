package com.malabar.malabarmoviesapp.ui.tv

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
import com.malabar.malabarmoviesapp.domain.data.tv.TvItem
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.item.MovieItemScreen

@Composable
fun TvShowsComposable(
    title: String,
    items: List<TvItem>,
    navController: NavController
){
    Column {
        Text(
            text = title,
            modifier = Modifier.padding(5.dp),
            fontWeight = FontWeight.Bold
        )

        LazyRow {
            items(items) { airingToday ->
                MovieItemScreen(
                    image = airingToday.createPosterImage(),
                    title = airingToday.name,
                    onClick = {
                        navController.navigate(Screens.TvDetails.createTvDetailsRoute(airingToday.id))
                    },
                    height = 200.dp
                )
            }
        }
    }
}

