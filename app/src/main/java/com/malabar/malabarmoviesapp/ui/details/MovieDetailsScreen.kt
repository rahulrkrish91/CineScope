package com.malabar.malabarmoviesapp.ui.details

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_ORIGINAL
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500
import com.malabar.core.ui.AsyncImageWithPlaceholder
import com.malabar.core.ui.CommonToolbarWithTrailingIcons
import com.malabar.core.ui.ExpandableText
import com.malabar.core.ui.MovieItem
import com.malabar.malabarmoviesapp.R
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.MovieCreditsState
import com.malabar.malabarmoviesapp.ui.MovieDetailsState
import com.malabar.malabarmoviesapp.ui.MovieDetailsViewModel
import com.malabar.malabarmoviesapp.ui.MovieRecommendedState
import com.malabar.malabarmoviesapp.ui.MovieSimilarState
import com.malabar.malabarmoviesapp.ui.details.cast.MovieCastScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    id: Int?,
    navController: NavController,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel()
) {

    val movieDetailsState = movieDetailsViewModel.mutableDetailsState.collectAsState()
    val movieCreditsState = movieDetailsViewModel.mutableCreditsState.collectAsState()
    val movieRecommendedState =
        movieDetailsViewModel.mutableRecommendedState.collectAsStateWithLifecycle()
    val movieSimilarState = movieDetailsViewModel.mutableSimilarState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (id != null) {
            movieDetailsViewModel.retrieveMovieDetails(id)
            movieDetailsViewModel.retrieveMovieCredits(id)
            movieDetailsViewModel.retrieveRecommendedMovies(id)
            movieDetailsViewModel.retrieveSimilarMovies(id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CommonToolbarWithTrailingIcons(
            title = stringResource(com.malabar.core.R.string.details),
            navController = navController,
            onGalleryClick = {
                if (id != null) {
                    navController.navigate(
                        Screens.Images.createRoute(id)
                    )
                }
            },
            onVideoClick = {
                if (id != null) {
                    navController.navigate(
                        Screens.Videos.createRoute(id)
                    )
                }
            },
            onReviewClick = {
                if (id != null) {
                    navController.navigate(
                        Screens.MovieReview.createMovieReviewRoute(id)
                    )
                }
            }
        )

        when (val state = movieDetailsState.value) {

            is MovieDetailsState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is MovieDetailsState.Success -> {
                val detailsResult = state.result
                Column {
                    /*AsyncImageWithPlaceholder(
                        model = "$MOVIE_IMAGE_ORIGINAL${detailsResult.backdrop_path}",
                        contentDescription = detailsResult.title,
                    )*/
                    AsyncImage(
                        model = "$MOVIE_IMAGE_ORIGINAL${detailsResult.backdrop_path}",
                        contentDescription = detailsResult.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop,
                        error = painterResource(com.malabar.core.R.drawable.no_image),
                        placeholder = painterResource(com.malabar.core.R.drawable.loading)
                    )

                    Text(
                        text = detailsResult.title,
                        modifier = Modifier.padding(5.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    ExpandableText(
                        text = detailsResult.overview,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(5.dp)
                    )

                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        detailsResult.genres.map {
                            AssistChip(
                                modifier = Modifier.padding(5.dp),
                                label = {
                                    Text(
                                        text = it.name
                                    )
                                },
                                onClick = {}
                            )
                        }
                    }

                    if (detailsResult.production_companies.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.production_companies),
                            modifier = Modifier.padding(5.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row {
                            detailsResult.production_companies.map {
                                MovieItem(
                                    image = "$MOVIE_IMAGE_W_500${it.logo_path}",
                                    title = it.name,
                                    onClick = {},
                                    height = 150.dp,
                                    width = 128.dp
                                )
                            }
                        }
                    }

                    Text(
                        text = stringResource(R.string.cast),
                        modifier = Modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )


                    when (val creditState = movieCreditsState.value) {
                        MovieCreditsState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is MovieCreditsState.Success -> {
                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState())
                            ) {
                                creditState.result.cast.map { cast ->
                                    cast.profile_path?.let {
                                        MovieCastScreen(
                                            cast.name,
                                            cast.character,
                                            it,
                                            cast.id,
                                            navController = navController
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Text(
                        text = stringResource(R.string.recommended),
                        modifier = Modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    when (val recommendedState = movieRecommendedState.value) {
                        MovieRecommendedState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is MovieRecommendedState.Success -> {
                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState())
                            ) {
                                recommendedState.result.results.map { recommended ->
                                    recommended.title?.let {
                                        MovieItem(
                                            image = recommended.createBackdropImage(),
                                            title = it,
                                            onClick = {
                                                navController.navigate(
                                                    Screens.Details.createRoute(
                                                        recommended.id
                                                    )
                                                )
                                            },
                                            height = 200.dp,
                                            width = 128.dp
                                        )
                                    }
                                }
                            }
                        }
                    }



                    when (val similarState = movieSimilarState.value) {

                        MovieSimilarState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is MovieSimilarState.Success -> {
                            if (similarState.result.results.isNotEmpty()) {
                                Text(
                                    text = stringResource(R.string.similar),
                                    modifier = Modifier.padding(5.dp),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    modifier = Modifier.horizontalScroll(rememberScrollState())
                                ) {
                                    similarState.result.results.map { similar ->
                                        similar.title?.let {
                                            MovieItem(
                                                image = similar.createBackdropImage(),
                                                title = it,
                                                onClick = {
                                                    navController.navigate(
                                                        Screens.Details.createRoute(
                                                            similar.id
                                                        )
                                                    )
                                                },
                                                height = 200.dp,
                                                width = 128.dp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen(1, rememberNavController())
}