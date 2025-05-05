package com.malabar.malabarmoviesapp.ui.details.cast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.malabar.malabarmoviesapp.ui.MovieCaseDetailsState
import com.malabar.malabarmoviesapp.ui.MovieDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastInfoScreen(
    id: Int,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel()
) {

    val movieCastInfoState = movieDetailsViewModel.mutableCastDetailsState.collectAsState()

    LaunchedEffect(Int) {
        movieDetailsViewModel.retrieveMovieCastDetails(id)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
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

    }
}

@Preview(showSystemUi = true)
@Composable
fun CastInfoScreenPreview() {
    CastInfoScreen(1)
}