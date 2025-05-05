package com.malabar.malabarmoviesapp.ui.tv.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.malabar.core.AppConstants.Companion.getVideThumbnail
import com.malabar.core.R
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.core.ui.MovieItem
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoItem
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.details.cast.CastInfoScreen
import com.malabar.malabarmoviesapp.ui.details.video.MovieVideoItem
import com.malabar.malabarmoviesapp.ui.tv.TvSeasonDetailsState
import com.malabar.malabarmoviesapp.ui.tv.TvSeasonVideoState
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowSeasonDetails(
    navController: NavController,
    seriesId: Int?,
    seasonNumber: Int?,
    tvViewModel: TvViewModel = koinViewModel()
) {

    val tvSeason = tvViewModel.mutableTvSeasonDetailsState.collectAsState()
    val tvSeasonVideo = tvViewModel.mutableTvSeasonVideoState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (seriesId != null && seasonNumber != null) {
            tvViewModel.retrieveTvSeasonDetails(
                seriesId, seasonNumber
            )
            tvViewModel.retrieveTvSeasonVideo(seriesId = seriesId, seasonNumber = seasonNumber)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        CommonToolbarBackNav(
            title = stringResource(R.string.details),
            navController = navController,
            onClick = {
                navController.popBackStack()
            }
        )

        when (val seasonDetails = tvSeason.value) {
            TvSeasonDetailsState.Loading -> {

            }

            is TvSeasonDetailsState.Success -> {
                AsyncImage(
                    model = seasonDetails.data.createPoster(),
                    contentDescription = "Image",
                    modifier = Modifier.height(250.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = seasonDetails.data.name,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = seasonDetails.data.overview,
                    modifier = Modifier.padding(5.dp)
                )

                Text(
                    text = "Episodes",
                    modifier = Modifier.padding(5.dp)
                )

                LazyRow {
                    items(seasonDetails.data.episodes) { episodes ->
                        MovieItem(
                            image = episodes.createPoster(),
                            title = "Ep.${episodes.episode_number}. ${episodes.name}",
                            onClick = {
                                seriesId?.let {
                                    seasonNumber?.let { it1 ->
                                        navController.navigate(
                                            Screens.TvEpisodeDetails.createRoute(
                                                seriesId = it,
                                                seasonNumber = it1,
                                                episodeNumber = episodes.episode_number
                                            )
                                        )
                                    }
                                }
                            },
                            height = 200.dp,
                            width = 128.dp
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.video),
                    modifier = Modifier.padding(5.dp)
                )
                when (val seasonVideo = tvSeasonVideo.value) {
                    TvSeasonVideoState.Loading -> {

                    }

                    is TvSeasonVideoState.Success -> {
                        LazyRow {
                            items(seasonVideo.data.results) { item ->
                                TvSeasonVideo(item, navController)
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun TvSeasonVideo(movieVideoItem: MovieVideoItem, navController: NavController) {
    OutlinedCard(
        modifier = Modifier
            .width(250.dp)
            .height(250.dp)
            .padding(5.dp),
        onClick = {
            navController.navigate(
                Screens.PlayVideo.createPlayVideoRoute(movieVideoItem.key)
            )
        }
    ) {
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = getVideThumbnail(movieVideoItem.key),
                contentDescription = "Movie video thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.play_button),
                contentDescription = "Play Video",
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(20.dp)
                    .size(60.dp)
            )
        }
        Text(
            text = "${movieVideoItem.name} | ${movieVideoItem.type}",
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun TvShowSeasonDetailsPreview() {
    TvShowSeasonDetails(rememberNavController(), 1, 1)

}