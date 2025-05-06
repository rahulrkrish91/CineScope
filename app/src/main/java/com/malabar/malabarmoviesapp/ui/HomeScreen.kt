package com.malabar.malabarmoviesapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.malabar.core.AppConstants.Companion.GOOGLE_PUB_SUB
import com.malabar.core.AppConstants.Companion.MOVIE_DEFAULT_LANG
import com.malabar.core.AppConstants.Companion.MOVIE_DEFAULT_REGION
import com.malabar.malabarmoviesapp.R
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

        Column {
            // Your app UI
            BannerAdView(adUnitId = GOOGLE_PUB_SUB)
        }

    }
}

@Composable
fun BannerAdView(adUnitId: String) {
    AndroidView(
        factory = { context ->
            AdView(context).apply {
                val adView = AdView(context)
                adView.setAdSize(AdSize.BANNER)
                adView.adUnitId = adUnitId
                adView.loadAd(AdRequest.Builder().build())
                adView
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    )
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}