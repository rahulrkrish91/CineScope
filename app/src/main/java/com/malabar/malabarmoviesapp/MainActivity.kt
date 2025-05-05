package com.malabar.malabarmoviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.malabar.core.ui.CommonToolbar
import com.malabar.malabarmoviesapp.navigation.Screens
import com.malabar.malabarmoviesapp.navigation.bottom_nav.BottomNavigationBar
import com.malabar.malabarmoviesapp.ui.HomeScreen
import com.malabar.malabarmoviesapp.ui.InAppUpdateViewModel
import com.malabar.malabarmoviesapp.ui.details.MovieDetailsScreen
import com.malabar.malabarmoviesapp.ui.details.images.MovieImagesScreen
import com.malabar.malabarmoviesapp.ui.details.video.MovieVideoScreen
import com.malabar.malabarmoviesapp.ui.details.video.PlayYTVideo
import com.malabar.malabarmoviesapp.ui.profile.ProfileScreen
import com.malabar.malabarmoviesapp.ui.review.MovieReviews
import com.malabar.malabarmoviesapp.ui.search.SearchResultList
import com.malabar.malabarmoviesapp.ui.search.SearchScreen
import com.malabar.malabarmoviesapp.ui.security.LoginScreen
import com.malabar.malabarmoviesapp.ui.theme.MalabarMoviesAppTheme
import com.malabar.malabarmoviesapp.ui.tv.TvScreen
import com.malabar.malabarmoviesapp.ui.tv.details.TvShowDetails
import com.malabar.malabarmoviesapp.ui.tv.details.TvShowEpisodeDetails
import com.malabar.malabarmoviesapp.ui.tv.details.TvShowSeasonDetails
import com.malabar.malabarmoviesapp.ui.tv.details.video.TvVideoScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    val updateViewModel: InAppUpdateViewModel by viewModel { parametersOf(this@MainActivity) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MalabarMoviesAppTheme {
                setContent {
                    MainScreen(updateViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(updateViewModel: InAppUpdateViewModel) {

    LaunchedEffect(Unit) {
        //updateViewModel.checkAndStartUpdate()
    }

    val navController = rememberNavController()
    //List of screens where the bottom bar should be displayed
    val bottomNavScreens = listOf(
        Screens.Home.route,
        Screens.Tv.route,
        Screens.Profile.route
    )


    val topAppBarScreens = listOf(
        Screens.Home.route,
        Screens.Tv.route
    )

    // Get current back stack entry
    val navBackStackEntry = navController.currentBackStackEntryAsState().value

    // Check if the current destination is in the list of screens to show the bottom bar
    val showBottomBar = navBackStackEntry?.destination?.route in bottomNavScreens
    val showTopBar = navBackStackEntry?.destination?.route in topAppBarScreens

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { if (showBottomBar) BottomNavigationBar(navController) },
        topBar = {
            if (showTopBar) {
                CommonToolbar(
                    onSearchClick = {
                        navController.navigate(Screens.Search.route)
                    }
                )
            }

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(
                route = Screens.Login.route
            ) {
                LoginScreen(navController)
            }

            composable(route = Screens.Home.route) {
                HomeScreen(navController)
            }
            composable(
                route = Screens.Details.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                MovieDetailsScreen(
                    id = id,
                    navController = navController
                )
            }

            composable(
                route = Screens.Images.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                MovieImagesScreen(
                    id = id,
                    navController = navController
                )
            }

            composable(
                route = Screens.Videos.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                MovieVideoScreen(
                    id = id,
                    navController = navController
                )
            }

            composable(
                route = Screens.PlayVideo.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                PlayYTVideo(
                    id = id,
                    navController = navController
                )
            }

            composable(route = Screens.Search.route) {
                SearchScreen(navController)
            }
            composable(
                route = Screens.SearchResult.route,
                arguments = listOf(
                    navArgument("query") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query")
                SearchResultList(query, navController)
            }

            composable(
                route = Screens.MovieReview.route,
                arguments = listOf(
                    navArgument("movieId") {
                        type = NavType.IntType
                    }
                )
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("movieId")
                MovieReviews(
                    id = id,
                    navController = navController
                )
            }

            composable(route = Screens.Profile.route) {
                ProfileScreen(navController)
            }

            composable(route = Screens.Tv.route) {
                TvScreen(navController = navController)
            }
            composable(
                route = Screens.TvDetails.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                TvShowDetails(
                    id = id,
                    navController = navController
                )
            }

            composable(
                route = Screens.TvSeasonDetails.route,
                arguments = listOf(
                    navArgument("seriesId") {
                        type = NavType.IntType
                    },
                    navArgument("seasonNumber") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val seriesId = backStackEntry.arguments?.getInt("seriesId")
                val seasonNumber = backStackEntry.arguments?.getInt("seasonNumber")
                TvShowSeasonDetails(
                    navController = navController,
                    seriesId = seriesId,
                    seasonNumber = seasonNumber
                )
            }

            composable(
                route = Screens.TvEpisodeDetails.route,
                arguments = listOf(
                    navArgument("seriesId") {
                        type = NavType.IntType
                    },
                    navArgument("seasonNumber") {
                        type = NavType.IntType
                    },
                    navArgument("episodeNumber") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val seriesId = backStackEntry.arguments?.getInt("seriesId")
                val seasonNumber = backStackEntry.arguments?.getInt("seasonNumber")
                val episodeNumber = backStackEntry.arguments?.getInt("episodeNumber")
                TvShowEpisodeDetails(
                    navController = navController,
                    seriesId = seriesId,
                    seasonNumber = seasonNumber,
                    episodeNumber = episodeNumber
                )
            }

            composable(
                route = Screens.TvSeasonVideo.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val seriesId = backStackEntry.arguments?.getInt("id")
                TvVideoScreen(
                    navController = navController,
                    id = seriesId
                )
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MalabarMoviesAppTheme {
        Greeting("Android")
    }
}