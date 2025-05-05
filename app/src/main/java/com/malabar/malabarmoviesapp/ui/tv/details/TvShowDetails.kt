package com.malabar.malabarmoviesapp.ui.tv.details

import android.R.attr.data
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.malabar.core.R
import com.malabar.core.failure.Failure
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.core.ui.CommonToolbarWithTrailingIcons
import com.malabar.core.ui.ExpandableText
import com.malabar.core.ui.MovieItem
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.details.cast.CastInfoScreen
import com.malabar.malabarmoviesapp.ui.tv.TvCreditsState
import com.malabar.malabarmoviesapp.ui.tv.TvRecommendationState
import com.malabar.malabarmoviesapp.ui.tv.TvSeriesDetailsState
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowDetails(
    id: Int?,
    navController: NavController,
    tvViewModel: TvViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val tvSeriesDetails = tvViewModel.mutableTvSeriesDetailsState.collectAsState()
    val tvSeriesCast = tvViewModel.mutableTvCreditsState.collectAsStateWithLifecycle()
    val tvRecommendation = tvViewModel.mutableTvRecommendationState.collectAsStateWithLifecycle()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    var showCastBottomSheet by remember { mutableStateOf(false) }
    val castSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    LaunchedEffect(Unit) {
        if (id != null) {
            tvViewModel.retrieveTvSeriesDetails(id)
            tvViewModel.retrieveTvCredits(id)
            tvViewModel.retrieveTvRecommendations(id)
        }
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
            .verticalScroll(rememberScrollState())
    ) {
        CommonToolbarWithTrailingIcons(
            title = stringResource(R.string.details),
            navController = navController,
            onGalleryClick = {
                if (id != null) {

                }
            },
            onVideoClick = {
                if (id != null) {
                    navController.navigate(
                        Screens.TvSeasonVideo.createTvVideo(id)
                    )
                }
            },
            onReviewClick = {
                if (id != null) {

                }
            }
        )
        when (val details = tvSeriesDetails.value) {
            TvSeriesDetailsState.Loading -> {

            }

            is TvSeriesDetailsState.Success -> {
                AsyncImage(
                    model = details.data.createBackdropImage(),
                    contentDescription = "Tv Show image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = details.data.name,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                ExpandableText(
                    text = details.data.overview,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(5.dp)
                )

                Text(
                    text = "Created by",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 16.sp
                )

                LazyRow {
                    items(details.data.created_by) { creator ->
                        AsyncImage(
                            model = creator.createProfilePath(),
                            contentDescription = creator.name,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(5.dp)
                                .clip(CircleShape)
                                .clickable {
                                    showBottomSheet = true
                                },
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.no_profile),
                            placeholder = painterResource(R.drawable.loading)
                        )
                        if (showBottomSheet) {
                            ModalBottomSheet(
                                modifier = Modifier.fillMaxHeight(),
                                sheetState = sheetState,
                                onDismissRequest = { showBottomSheet = false }
                            ) {
                                CastInfoScreen(creator.id)
                            }
                        }
                    }
                }

                Text(
                    text = "Cast",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 16.sp
                )
                when (val castDetails = tvSeriesCast.value) {
                    TvCreditsState.Loading -> {

                    }

                    is TvCreditsState.Success -> {
                        LazyRow {
                            items(castDetails.data.cast) { cast ->
                                AsyncImage(
                                    model = cast.getImageUrl(),
                                    contentDescription = cast.name,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            showCastBottomSheet = true
                                        },
                                    contentScale = ContentScale.Crop
                                )
                                if (showCastBottomSheet) {
                                    ModalBottomSheet(
                                        modifier = Modifier.fillMaxHeight(),
                                        sheetState = castSheetState,
                                        onDismissRequest = { showCastBottomSheet = false }
                                    ) {
                                        CastInfoScreen(cast.id)
                                    }
                                }
                            }
                        }
                    }
                }

                Text(
                    text = "Last episode to air",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 16.sp
                )

                MovieItem(
                    image = details.data.last_episode_to_air.createPosterImage(),
                    title = details.data.last_episode_to_air.name,
                    onClick = {
                    },
                    height = 200.dp,
                    width = 200.dp
                )

                Text(
                    text = "Seasons",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 16.sp
                )

                LazyRow {
                    items(details.data.seasons) { season ->
                        MovieItem(
                            image = season.createPosterImage(),
                            title = "${season.season_number}. ${season.name}",
                            onClick = {
                                navController.navigate(
                                    Screens.TvSeasonDetails.createRoute(
                                        seriesId = details.data.id,
                                        seasonNumber = season.season_number
                                    )
                                )
                            },
                            height = 200.dp,
                            width = 128.dp
                        )
                    }
                }

                Text(
                    text = "Recommendations",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 16.sp
                )
                when (val recommendations = tvRecommendation.value) {
                    TvRecommendationState.Loading -> {

                    }

                    is TvRecommendationState.Success -> {
                        LazyRow {
                            items(
                                recommendations.data.results
                            ) { data ->
                                MovieItem(
                                    image = data.createPosterImage(),
                                    title = data.name,
                                    onClick = {
                                        navController.navigate(
                                            Screens.TvDetails.createTvDetailsRoute(data.id)
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
}

@Preview(showSystemUi = true)
@Composable
fun TvShowDetailsPreview() {
    TvShowDetails(
        1, rememberNavController()
    )
}