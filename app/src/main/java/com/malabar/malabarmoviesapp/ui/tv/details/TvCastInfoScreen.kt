package com.malabar.malabarmoviesapp.ui.tv.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.malabar.core.ui.MovieItem
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.MovieCaseDetailsState
import com.malabar.malabarmoviesapp.ui.MovieDetailsViewModel
import com.malabar.malabarmoviesapp.ui.tv.TvCastCreditsState
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvCastInfoScreen(
    id: Int,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel(),
    tvViewModel: TvViewModel = koinViewModel(),
    navController: NavController
) {
    val movieCastInfoState = movieDetailsViewModel.mutableCastDetailsState.collectAsState()
    val tvCastCreditsState = tvViewModel.mutableTvCastCreditsState.collectAsStateWithLifecycle()

    LaunchedEffect(Int) {
        movieDetailsViewModel.retrieveMovieCastDetails(id)
        tvViewModel.retrieveTvCastCredits(id)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        when (val state = movieCastInfoState.value) {
            MovieCaseDetailsState.Loading -> {

            }

            is MovieCaseDetailsState.Success -> {
                AsyncImage(
                    model = state.result.getProfilePath(),
                    contentDescription = state.result.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(250.dp)
                )

                Text(
                    text = state.result.name,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                state.result.birthday?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
                Text(
                    text = state.result.biography,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }

        when (val tvCastCredit = tvCastCreditsState.value) {

            TvCastCreditsState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            is TvCastCreditsState.Success -> {
                Text(
                    text = "Tv Shows",
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                LazyRow {
                    items(tvCastCredit.data.cast) { credits ->
                        MovieItem(
                            image = credits.createPosterImage(),
                            title = credits.name,
                            onClick = {
                                navController.navigate(
                                    Screens.TvDetails.createTvDetailsRoute(credits.id)
                                )
                            },
                            height = 200.dp,
                            width = 150.dp
                        )
                    }

                }
            }
        }
    }
}