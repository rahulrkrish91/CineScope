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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.malabar.core.R
import com.malabar.core.failure.Failure
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.tv.TvSearchState
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchTvScreenResult(
    query: String?,
    navController: NavHostController,
    tvViewModel: TvViewModel = koinViewModel()
) {

    val searchState = tvViewModel.mutableTvSearchState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (query != null)
            tvViewModel.retrieveTvSearch(query)

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

            TvSearchState.Loading -> {

            }

            is TvSearchState.Success -> {
                LazyColumn {
                    items(state.data.results) { result ->
                        OutlinedCard(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp),
                            onClick = {
                                navController.navigate(
                                    Screens.TvDetails.createTvDetailsRoute(result.id)
                                )
                            }
                        ) {
                            Column {
                                AsyncImage(
                                    model = result.createBackdropImage(),
                                    contentDescription = "Movie Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.height(200.dp),
                                    error = painterResource(R.drawable.no_image),
                                    placeholder = painterResource(R.drawable.loading)
                                )
                                result.name?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .fillMaxWidth(),
                                        fontSize = 16.sp
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

@Preview(showSystemUi = true)
@Composable
fun SearchTvScreenResultPreview() {
    SearchTvScreenResult("", rememberNavController())
}