package com.malabar.malabarmoviesapp.navigation.bottom_nav

import androidx.annotation.DrawableRes
import com.malabar.core.R

data class NavigationItem(
    val title: String,
    @DrawableRes val drawable: Int,
    val route: String
)

val navigationItems = listOf(
    NavigationItem("Home", R.drawable.movie, "Home"),
    NavigationItem("TV", R.drawable.tv, "TV"),
    NavigationItem("Profile", R.drawable.user, "Profile")
)
