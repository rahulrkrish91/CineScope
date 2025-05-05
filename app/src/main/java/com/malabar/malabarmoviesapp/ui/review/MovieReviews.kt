package com.malabar.malabarmoviesapp.ui.review

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.malabar.core.R
import com.malabar.core.ui.CommonToolbarBackNav
import com.malabar.core.ui.ExpandableText
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.ui.MovieDetailsViewModel
import com.malabar.malabarmoviesapp.ui.MovieReviewsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieReviews(
    id: Int?,
    navController: NavController,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel()
) {

    val reviewState = movieDetailsViewModel.mutableMovieReviewState.collectAsState()

    LaunchedEffect(Unit) {
        if (id != null) {
            movieDetailsViewModel.retrieveMovieReviews(id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        CommonToolbarBackNav(
            title = stringResource(R.string.reviews),
            onClick = {
                if (id != null) {
                    Screens.Details.createRoute(id)
                }
            },
            navController
        )

        when (val reviews = reviewState.value) {
            MovieReviewsState.Loading -> {

            }

            is MovieReviewsState.Success -> {
                if (reviews.result.results.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_reviews),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    reviews.result.results.map { review ->
                        Column {
                            OutlinedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {
                                Row {
                                    AsyncImage(
                                        model = review.author_details.createAvathar(),
                                        contentDescription = "Avatar",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .padding(5.dp)
                                            .clip(CircleShape),
                                        error = painterResource(R.drawable.no_image),
                                        placeholder = painterResource(R.drawable.no_image)
                                    )
                                    Column {
                                        Text(
                                            text = review.author,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                        ExpandableText(
                                            text = Html.fromHtml(
                                                review.content,
                                                Html.FROM_HTML_MODE_LEGACY
                                            ).toString(),
                                            modifier = Modifier.padding(5.dp),
                                            fontSize = 15.sp,
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
}

@Preview(showBackground = true)
@Composable
fun MovieReviewsPreview() {
    MovieReviews(
        1, rememberNavController()
    )
}