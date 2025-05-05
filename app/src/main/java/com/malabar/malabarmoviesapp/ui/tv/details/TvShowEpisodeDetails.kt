package com.malabar.malabarmoviesapp.ui.tv.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.malabar.core.R
import com.malabar.malabarmoviesapp.domain.data.tv.cast.TvCastItem
import com.malabar.malabarmoviesapp.domain.data.tv.episode.EpisodeDetails
import com.malabar.malabarmoviesapp.ui.tv.TvEpisodeDetailsState
import com.malabar.malabarmoviesapp.ui.tv.TvEpisodeVideoState
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvShowEpisodeDetails(
    navController: NavController,
    seriesId: Int?,
    seasonNumber: Int?,
    episodeNumber: Int?,
    tvViewModel: TvViewModel = koinViewModel()
) {

    val episodeState = tvViewModel.mutableTvEpisodeDetailsState.collectAsState()
    val episodeVideoState = tvViewModel.mutableTvEpisodeVideoState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (seriesId != null && seasonNumber != null && episodeNumber != null) {
            tvViewModel.retrieveTvEpisodeDetails(
                seriesId = seriesId,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber
            )

            tvViewModel.retrieveTvEpisodeVideo(
                seriesId = seriesId,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber
            )
        }
    }
    when (val episodeDetails = episodeState.value) {
        TvEpisodeDetailsState.Loading -> {

        }

        is TvEpisodeDetailsState.Success -> {
            DetailsCompose(episodeDetails.data, episodeVideoState, navController)
        }
    }
}

@Composable
fun DetailsCompose(
    data: EpisodeDetails,
    episodeVideoState: State<TvEpisodeVideoState>,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Navigate back"
            )
        }

        AsyncImage(
            model = data.createStillPath(),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = data.name,
            modifier = Modifier.padding(5.dp)
        )

        Text(
            text = data.overview,
            modifier = Modifier.padding(5.dp)
        )

        Text(
            text = "Released on: ${data.air_date}",
            modifier = Modifier.padding(5.dp)
        )

        Text(
            text = "Crew",
            modifier = Modifier.padding(5.dp)
        )

        LazyRow {
            items(data.guest_stars) { cast ->
                AsyncImage(
                    model = cast.createProfile(),
                    contentDescription = cast.name,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(5.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        when(val episodeVideo = episodeVideoState.value){
            TvEpisodeVideoState.Loading -> {

            }
            is TvEpisodeVideoState.Success -> {
                Text(
                    text = stringResource(R.string.video),
                    modifier = Modifier.padding(5.dp)
                )
                LazyRow {
                    items(episodeVideo.data.results) { item->
                        TvSeasonVideo(item, navController)
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TvShowEpisodeDetailsPreview() {
    val episodeDetails = EpisodeDetails(
        air_date = "2023-01-15",
        episode_number = 1,
        guest_stars = listOf(
            TvCastItem(
                character = "Dr. Newman - Scientist #2",
                credit_id = "0",
                order = 1,
                adult = false,
                gender = 1,
                id = 1,
                known_for_department = "Acting",
                name = "John Hannah",
                original_name = "John Hannah",
                popularity = 1.0,
                profile_path = "https://image.tmdb.org/t/p/w500/8GzJhaZrChZpv84SU4vAsvsR3cl.jpg"
            )
        ),
        name = "When You're Lost in the Darkness",
        overview = "",
        id = 1,
        runtime = 1,
        season_number = 1,
        still_path = "https://image.tmdb.org/t/p/w500/3VeY1k8wFyhcrMyQ8jpGegh9beU.jpg",
        vote_average = 1.1,
        vote_count = 1
    )
    //DetailsCompose(episodeDetails, episodeVideoState, rememberNavController())
}