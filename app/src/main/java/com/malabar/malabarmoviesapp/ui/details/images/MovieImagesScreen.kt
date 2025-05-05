package com.malabar.malabarmoviesapp.ui.details.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.MovieDetailsViewModel
import com.malabar.malabarmoviesapp.ui.MovieImagesState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieImagesScreen(
    id: Int?,
    navController: NavController,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel()
) {

    val movieImageState = movieDetailsViewModel.mutableMovieImageState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (id != null) {
            movieDetailsViewModel.retrieveMovieImages(id)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        CommonToolbarBackNav(
            title = stringResource(com.malabar.core.R.string.images),
            navController = navController,
            onClick = {
                if (id != null) {
                    navController.navigate(
                        Screens.Details.createRoute(id)
                    )
                }
            }
        )

        when (val state = movieImageState.value) {
            MovieImagesState.Loading -> {

            }

            is MovieImagesState.Success -> {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {

                    items(state.result.backdrops) { images ->

                        AsyncImage(
                            model = images.createImagePath(),
                            contentDescription = "Movie Image",
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MovieImagesScreenPreview() {
    MovieImagesScreen(1, rememberNavController())
}