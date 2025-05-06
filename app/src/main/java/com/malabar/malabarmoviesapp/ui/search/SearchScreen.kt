package com.malabar.malabarmoviesapp.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.malabar.core.data.MovieItem
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.core.ui.CommonToolbarWithTrailingIcons
import com.malabar.malabarmoviesapp.R
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.MovieSearchState
import com.malabar.malabarmoviesapp.ui.MovieSearchViewModel
import com.malabar.malabarmoviesapp.ui.MovieTrendingPersonState
import com.malabar.malabarmoviesapp.ui.MovieTrendingState
import com.malabar.malabarmoviesapp.ui.details.cast.CastInfoScreen
import com.malabar.malabarmoviesapp.ui.tv.TvTrendingState
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    movieSearchViewModel: MovieSearchViewModel = koinViewModel(),
    tvViewModel: TvViewModel = koinViewModel()
) {


    val trendingMoviesState =
        movieSearchViewModel.mutableMovieTrendingState.collectAsStateWithLifecycle()
    val trendingTvState = tvViewModel.mutableTrendingTvState.collectAsStateWithLifecycle()
    val trendingPersonState =
        movieSearchViewModel.mutableTrendingPersonState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        movieSearchViewModel.retrieveTrendingMovies(timeWindow = "day")
        tvViewModel.retrieveTrendingTvShows(time_window = "day")
        movieSearchViewModel.retrieveTrendingPerson(timeWindow = "day")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CommonToolbarBackNav(
            title = stringResource(com.malabar.core.R.string.search),
            navController = navController,
            onClick = {}
        )

        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("Search Movies, Tv shows or People") },
            modifier = Modifier
                .padding(top = 5.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isEmpty()) {
                            scope.launch {
                                snackBarHostState.showSnackbar("Please enter some text to search")
                            }
                        } else {
                            navController.navigate(Screens.SearchResult.createRoute(text))
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(com.malabar.core.R.drawable.search),
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
        )
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.trending_movies),
                modifier = Modifier.padding(top = 5.dp, start = 16.dp, end = 16.dp)
            )
            when (val tendingMovies = trendingMoviesState.value) {
                MovieTrendingState.Loading -> {

                }

                is MovieTrendingState.Success -> {
                    LazyRow(
                        modifier = Modifier.padding(top = 5.dp, start = 16.dp, end = 16.dp)
                    ) {
                        items(tendingMovies.result.results) { trending ->
                            trending.title?.let {
                                com.malabar.core.ui.MovieItem(
                                    image = trending.createPosterImage(),
                                    title = it,
                                    onClick = {
                                        navController.navigate(
                                            Screens.Details.createRoute(trending.id)
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

            Text(
                text = stringResource(R.string.trending_tv_shows),
                modifier = Modifier.padding(top = 5.dp, start = 16.dp, end = 16.dp)
            )
            when (val trendingTv = trendingTvState.value) {
                TvTrendingState.Loading -> {

                }

                is TvTrendingState.Success -> {
                    LazyRow(
                        modifier = Modifier.padding(top = 5.dp, start = 16.dp, end = 16.dp)
                    ) {
                        items(trendingTv.data.results) { shows ->
                            com.malabar.core.ui.MovieItem(
                                image = shows.createPosterImage(),
                                title = "",
                                onClick = {
                                    navController.navigate(
                                        Screens.TvDetails.createTvDetailsRoute(shows.id)
                                    )
                                },
                                height = 200.dp,
                                width = 128.dp
                            )
                        }
                    }
                }
            }

            Text(
                text = stringResource(R.string.trending_tv_people),
                modifier = Modifier.padding(top = 5.dp, start = 16.dp, end = 16.dp)
            )

            var showBottomSheet by remember { mutableStateOf(false) }
            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = false,
            )

            when (val trendingPerson = trendingPersonState.value) {
                MovieTrendingPersonState.Loading -> {

                }

                is MovieTrendingPersonState.Success -> {
                    LazyRow(
                        modifier = Modifier.padding(top = 5.dp, start = 16.dp, end = 16.dp)
                    ) {
                        items(trendingPerson.result.results) { trendingPerson ->
                            AsyncImage(
                                model = trendingPerson.createProfilePath(),
                                contentDescription = trendingPerson.name,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .height(100.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        showBottomSheet = true
                                    },
                                contentScale = ContentScale.Crop
                            )
                            if (showBottomSheet) {
                                ModalBottomSheet(
                                    modifier = Modifier.fillMaxHeight(),
                                    sheetState = sheetState,
                                    onDismissRequest = { showBottomSheet = false }
                                ) {
                                    CastInfoScreen(trendingPerson.id)
                                    /*Text(
                                        "Swipe up to open sheet. Swipe down to dismiss.",
                                        modifier = Modifier.padding(16.dp)
                                    )*/
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
fun SearchScreenPreview() {
    SearchScreen(rememberNavController())
}