package com.malabar.malabarmoviesapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.malabar.core.AppConstants.Companion.MOVIE_DEFAULT_LANG
import com.malabar.core.AppConstants.Companion.MOVIE_DEFAULT_REGION
import com.malabar.core.ui.CommonToolbar
import com.malabar.malabarmoviesapp.R
import com.malabar.malabarmoviesapp.navigation.Screens
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    movieViewModel: MovieViewModel = koinViewModel()
) {

    val nowPlayingState = movieViewModel.mutableNowPlayingMovie.collectAsStateWithLifecycle()
    val popularMovieState = movieViewModel.mutablePopularMovie.collectAsStateWithLifecycle()
    val topRatedMovieState = movieViewModel.mutableTopRatedMovie.collectAsStateWithLifecycle()
    val upcomingMovieState = movieViewModel.mutableUpcomingMovie.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        movieViewModel.retrieveNowPlayingMovies(
            language = MOVIE_DEFAULT_LANG,
            page = 1,
            region = MOVIE_DEFAULT_REGION
        )
        movieViewModel.retrievePopularMovies(
            language = MOVIE_DEFAULT_LANG,
            page = 1,
            region = MOVIE_DEFAULT_REGION
        )
        movieViewModel.retrieveTopRatedMovies(
            language = MOVIE_DEFAULT_LANG,
            page = 1,
            region = MOVIE_DEFAULT_REGION
        )
        movieViewModel.retrieveUpcomingMovies(
            language = MOVIE_DEFAULT_LANG,
            page = 1,
            region = MOVIE_DEFAULT_REGION
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        val result = nowPlayingState.value as MovieNowPlayingState.Success
        HomeScreenMovieItems(
            rowTitle = stringResource(R.string.now_playing),
            items = result.movieNowPlayingResponse.results,
            navController = navController
        )
        val popularResult = popularMovieState.value as MoviePopularState.Success
        HomeScreenMovieItems(
            rowTitle = stringResource(R.string.popular),
            items = popularResult.moviePopularResponse.results,
            navController = navController
        )

        val topRatedResult = topRatedMovieState.value as MovieTopRatedState.Success
        HomeScreenMovieItems(
            rowTitle = stringResource(R.string.top_rated),
            items = topRatedResult.moviePopularResponse.results,
            navController = navController
        )

        val upcomingResult = upcomingMovieState.value as MovieUpcomingState.Success
        HomeScreenMovieItems(
            rowTitle = stringResource(R.string.upcoming),
            items = upcomingResult.movieNowPlayingResponse.results,
            navController = navController
        )

    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}