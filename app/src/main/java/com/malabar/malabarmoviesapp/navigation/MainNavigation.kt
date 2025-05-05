package com.malabar.malabarmoviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.malabar.malabarmoviesapp.ui.HomeScreen
import com.malabar.malabarmoviesapp.ui.details.MovieDetailsScreen
import com.malabar.malabarmoviesapp.ui.details.images.MovieImagesScreen
import com.malabar.malabarmoviesapp.ui.details.video.MovieVideoScreen
import com.malabar.malabarmoviesapp.ui.search.SearchScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(
            Screens.Home.route
        ) {
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
            Screens.Search.route
        ) {
            SearchScreen(navController)
        }
    }
}