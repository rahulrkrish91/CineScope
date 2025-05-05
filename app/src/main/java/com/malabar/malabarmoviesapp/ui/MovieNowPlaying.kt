package com.malabar.malabarmoviesapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.malabar.core.AppConstants.Companion.MOVIE_DEFAULT_LANG
import com.malabar.core.AppConstants.Companion.MOVIE_DEFAULT_REGION
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_ORIGINAL
import com.malabar.core.ui.MovieItem
import org.koin.androidx.compose.koinViewModel
import com.malabar.malabarmoviesapp.R
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

@Composable
fun MovieNowPlaying(modifier: Modifier, movieViewModel: MovieViewModel = koinViewModel()) {

    val nowPlayingState = movieViewModel.mutableNowPlayingMovie.collectAsStateWithLifecycle()
    val popularMovieState = movieViewModel.mutablePopularMovie.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        runBlocking {
            val nowPlaying = async {
                movieViewModel.retrieveNowPlayingMovies(
                    language = MOVIE_DEFAULT_LANG,
                    page = 1,
                    region = MOVIE_DEFAULT_REGION
                )
            }
            val popularMovie = async {
                movieViewModel.retrievePopularMovies(
                    language = MOVIE_DEFAULT_LANG,
                    page = 1,
                    region = MOVIE_DEFAULT_REGION
                )
            }
            nowPlaying.await()
            popularMovie.await()
        }

    }

    Column {
        Text(
            text = stringResource(R.string.now_playing)
        )
        val result = nowPlayingState.value as MovieNowPlayingState.Success

        LazyRow {
            items(result.movieNowPlayingResponse.results) { movie ->
                MovieItem(
                    image = "$MOVIE_IMAGE_ORIGINAL${movie.poster_path}",
                    title = movie.title,
                    onClick = {},
                    height = 200.dp,
                    width = 128.dp
                )
            }
        }

        Text(
            text = stringResource(R.string.popular)
        )
        val popularResult = popularMovieState.value as MoviePopularState.Success

        LazyRow {
            items(popularResult.moviePopularResponse.results) { movie ->
                MovieItem(
                    image = "$MOVIE_IMAGE_ORIGINAL${movie.poster_path}",
                    title = movie.title,
                    onClick = {},
                    height = 200.dp,
                    width = 128.dp
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MovieNowPlayingPreview() {
    MovieNowPlaying(modifier = Modifier)
}