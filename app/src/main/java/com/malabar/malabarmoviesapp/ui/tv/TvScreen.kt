package com.malabar.malabarmoviesapp.ui.tv

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.malabar.core.R
import com.malabar.core.failure.Failure
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvScreen(
    navController: NavController,
    tvViewModel: TvViewModel = koinViewModel()
) {

    val tvAiringTodayState = tvViewModel.airingTodayState.collectAsStateWithLifecycle()
    val tvOnAirState = tvViewModel.mutableOnTheAirState.collectAsStateWithLifecycle()
    val tvPopularState = tvViewModel.mutablePopularTvState.collectAsStateWithLifecycle()
    val tvTopRatedState = tvViewModel.mutableTopRatedTvState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        tvViewModel.retrieveAiringToday()
        tvViewModel.retrieveOnAir()
        tvViewModel.retrievePopularTv()
        tvViewModel.retrieveTopRatedTv()
        tvViewModel.failure.collect { failure ->
            when (failure) {
                Failure.InternalError -> {
                    Toast.makeText(context, "Internal Error", Toast.LENGTH_SHORT).show()
                }

                is Failure.ServerError -> {
                    Toast.makeText(
                        context,
                        "Server Error ${failure.throwable?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        when (val state = tvAiringTodayState.value) {
            TvAiringTodayState.Loading -> {

            }

            is TvAiringTodayState.Success -> {
                TvShowsComposable(
                    title = stringResource(R.string.airing_today),
                    items = state.data.results,
                    navController = navController
                )
            }
        }

        when (val onAirState = tvOnAirState.value) {
            TvOnTheAirState.Loading -> {

            }

            is TvOnTheAirState.Success -> {
                TvShowsComposable(
                    title = stringResource(R.string.on_the_air),
                    items = onAirState.data.results,
                    navController = navController
                )
            }
        }

        when (val popularTvState = tvPopularState.value) {
            PopularTvShowState.Loading -> {

            }
            is PopularTvShowState.Success -> {
                TvShowsComposable(
                    title = stringResource(com.malabar.malabarmoviesapp.R.string.popular),
                    items = popularTvState.data.results,
                    navController = navController
                )
            }
        }
        when(val topRatedTv = tvTopRatedState.value){
            TopRatedTvShowState.Loading -> {

            }
            is TopRatedTvShowState.Success -> {
                TvShowsComposable(
                    title = stringResource(com.malabar.malabarmoviesapp.R.string.top_rated),
                    items = topRatedTv.data.results,
                    navController = navController
                )
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun TvScreenPreview() {
    TvScreen(rememberNavController())
}