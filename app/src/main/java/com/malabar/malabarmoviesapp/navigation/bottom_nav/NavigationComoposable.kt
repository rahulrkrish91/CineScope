package com.malabar.malabarmoviesapp.navigation.bottom_nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.malabar.core.R
import com.malabar.malabarmoviesapp.navigation.Screens

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    val navigationItems = listOf(
        NavigationItem(
            title = "Movie",
            drawable = R.drawable.movie,
            route = Screens.Home.route
        ),
        NavigationItem(
            title = "TV",
            drawable = R.drawable.tv,
            route = Screens.Tv.route
        ),
        NavigationItem(
            title = "Profile",
            drawable = R.drawable.user,
            route = Screens.Profile.route
        )
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.drawable),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}