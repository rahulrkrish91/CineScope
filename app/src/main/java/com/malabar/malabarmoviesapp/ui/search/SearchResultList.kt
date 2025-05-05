package com.malabar.malabarmoviesapp.ui.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.malabar.core.data.MovieItem
import com.malabar.core.failure.Failure
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.MovieSearchState
import com.malabar.malabarmoviesapp.ui.MovieSearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchResultList(
    query: String?,
    navController: NavController,
    movieSearchViewModel: MovieSearchViewModel = koinViewModel(),
) {

    val searchState = movieSearchViewModel.movieSearchState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (query != null)
            movieSearchViewModel.retrieveMovieSearch(query)

        movieSearchViewModel.failure.collect { failure ->
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
            .fillMaxSize()
    ) {
        CommonToolbarBackNav(
            title = stringResource(com.malabar.core.R.string.search_result),
            navController = navController,
            onClick = {
                navController.popBackStack()
            }
        )
        when (val state = searchState.value) {

            MovieSearchState.Loading -> {

            }
            is MovieSearchState.Success -> {
                LazyColumn {
                    items(state.result.results) { result ->
                        SearchResultScreen(result, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultScreen(searchResult: MovieItem, navController: NavController) {
    OutlinedCard(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp),
        onClick = {
            navController.navigate(
                Screens.Details.createRoute(searchResult.id)
            )
        }
    ) {
        Column {
            AsyncImage(
                model = searchResult.createBackdropImage(),
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(200.dp),
            )
            Text(
                text = searchResult.title,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                fontSize = 16.sp
            )
        }
    }
}