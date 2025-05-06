package com.malabar.malabarmoviesapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.malabar.core.R

sealed class Screens(val route: String) {
    object Login : Screens("login")
    object Home : Screens("home")
    object Details : Screens("details/{id}") {
        fun createRoute(id: Int) = "details/$id"
    }

    object Images : Screens("images/{id}") {
        fun createRoute(id: Int) = "images/$id"
    }

    object Videos : Screens("videos/{id}") {
        fun createRoute(id: Int) = "videos/$id"
    }

    object PlayVideo : Screens("playVideo/{id}") {
        fun createPlayVideoRoute(id: String) = "playVideo/$id"
    }

    object Search : Screens("search")

    object SearchResult : Screens("search_result/{query}") {
        fun createRoute(query: String) = "search_result/$query"
    }

    object SearchTv : Screens("searchTv")

    object SearchTvResult : Screens("searchTvResult/{query}") {
        fun createTvSearchRoute(query: String) = "searchTvResult/$query"
    }

    object MovieReview : Screens("movieReviews/{movieId}") {
        fun createMovieReviewRoute(movieId: Int) = "movieReviews/$movieId"
    }

    object Profile : Screens("profile")

    object Tv : Screens("tv")

    object TvDetails : Screens("tvDetails/{id}") {
        fun createTvDetailsRoute(id: Int) = "tvDetails/$id"
    }

    object TvSeasonDetails : Screens("season/{seriesId}/{seasonNumber}") {
        fun createRoute(seriesId: Int, seasonNumber: Int) = "season/$seriesId/$seasonNumber"
    }

    object TvEpisodeDetails : Screens("episode/{seriesId}/{seasonNumber}/{episodeNumber}") {
        fun createRoute(seriesId: Int, seasonNumber: Int, episodeNumber: Int) =
            "episode/$seriesId/$seasonNumber/$episodeNumber"
    }

    object TvSeasonVideo : Screens("tvVideo/{id}") {
        fun createTvVideo(id: Int) = "tvVideo/$id"
    }
}

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val drawable: Int
) {
    object Movie : BottomNavigationScreens("Home", R.string.home, R.drawable.movie)
    object TV : BottomNavigationScreens("TV", R.string.tv, R.drawable.tv)
    object Profile : BottomNavigationScreens("Profile", R.string.profile, R.drawable.user)
}