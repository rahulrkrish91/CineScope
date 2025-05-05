package com.malabar.malabarmoviesapp.ui.tv.details.video

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.malabar.core.failure.Failure
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoItem
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.MovieVideoState
import com.malabar.malabarmoviesapp.ui.details.video.MovieVideoItem
import com.malabar.malabarmoviesapp.ui.tv.TvSeriesVideoState
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvVideoScreen(
    id: Int?,
    navController: NavController,
) {

    val context = LocalContext.current
    val tvViewModel: TvViewModel = koinViewModel()

    val tvVideoState = tvViewModel.mutableTvVideoState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (id != null) {
            tvViewModel.retrieveTvVideo(id)
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
    ) {
        CommonToolbarBackNav(
            title = stringResource(R.string.video),
            navController = navController,
            onClick = {
                navController.popBackStack()
            }
        )
        when (val state = tvVideoState.value) {

            TvSeriesVideoState.Loading -> {

            }
            is TvSeriesVideoState.Success -> {
                if (state.data.results.isEmpty()) {
                    Text(text = "No videos found")
                } else {
                    LazyColumn {
                        items(state.data.results) { item ->
                            MovieVideoItem(item, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieVideoItem(movieVideoItem: MovieVideoItem, navController: NavController) {
    OutlinedCard(
        modifier = Modifier
            .wrapContentSize()
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
fun TvVideoScreenPreview() {
    TvVideoScreen(
        1, rememberNavController()
    )
}