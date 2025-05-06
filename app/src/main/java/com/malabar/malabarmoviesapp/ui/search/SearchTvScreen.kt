package com.malabar.malabarmoviesapp.ui.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.malabar.core.failure.Failure
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.malabarmoviesapp.R
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.tv.TvTrendingState
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchTvScreen(
    navController: NavHostController,
    tvViewModel: TvViewModel = koinViewModel()
) {
    val trendingTvState = tvViewModel.mutableTrendingTvState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        tvViewModel.retrieveTrendingTvShows(time_window = "day")
        tvViewModel.failure.collect {
            when (it) {
                Failure.InternalError -> {
                    Toast.makeText(context, "Internal Error", Toast.LENGTH_LONG).show()
                }

                is Failure.ServerError -> {
                    Toast.makeText(context, "Error ${it.throwable}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CommonToolbarBackNav(
            title = stringResource(com.malabar.core.R.string.search_tv_shows),
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
                            navController.navigate(Screens.SearchTvResult.createTvSearchRoute(text))
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
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun SearchTvScreenPreview() {
    SearchTvScreen(rememberNavController())
}