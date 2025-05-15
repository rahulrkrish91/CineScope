package com.malabar.malabarmoviesapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.malabar.core.AnalyticsHelper
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
import com.malabar.malabarmoviesapp.ui.search.SearchTvScreen
import com.malabar.malabarmoviesapp.ui.search.SearchTvScreenResult
import com.malabar.malabarmoviesapp.ui.security.AuthViewModel
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
    val authViewModel: AuthViewModel by viewModel()

    /*private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var oneTapClient: SignInClient

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    firebaseAuth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                handleSignInResult(idToken)
                                Log.d("FirebaseAuth", "signInWithCredential:success")
                            } else {
                                AnalyticsHelper.logEvent(
                                    name = "MainActivity_Google_Login",
                                    params = mapOf("Google Login Failed" to task.exception?.localizedMessage)
                                )
                                Log.w(
                                    "FirebaseAuth",
                                    "signInWithCredential:failure",
                                    task.exception
                                )
                            }
                        }
                }
            }
        }

    private fun handleSignInResult(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseAuth", "signInWithCredential:success")
                    fetchApiKeyFromDatabase()
                } else {
                    AnalyticsHelper.logEvent(
                        name = "MainActivity_Google_Login",
                        params = mapOf("Google Login Failed" to task.exception?.localizedMessage)
                    )
                    Toast.makeText(applicationContext, task.exception?.cause?.localizedMessage, Toast.LENGTH_SHORT).show()
                    Log.w("FirebaseAuth", "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun fetchApiKeyFromDatabase() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val databaseRef = FirebaseDatabase.getInstance()
                .getReference("api_key") // or just "api_keys/key" if it's shared
            databaseRef.get()
                .addOnSuccessListener { dataSnapshot ->
                    val apiKey = dataSnapshot.getValue(String::class.java)
                    Log.d("API_KEY", "Fetched key: $apiKey")
                    // Use your API key here
                    setContent {
                        MainScreen(updateViewModel, launcher, oneTapClient)
                    }
                }
                .addOnFailureListener { e ->
                    AnalyticsHelper.logEvent(
                        name = "MainActivity_Google_Login",
                        params = mapOf("Fetch Api Key from Firebase DB" to e.localizedMessage)
                    )
                    Log.e("API_KEY", "Failed to fetch key", e)
                }
        } else {
            Toast.makeText(applicationContext, "User not signed in, cannot fetch key", Toast.LENGTH_SHORT).show()
            Log.w("API_KEY", "User not signed in, cannot fetch key")
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //oneTapClient = Identity.getSignInClient(this)
        MobileAds.initialize(this)
        setContent {
            MalabarMoviesAppTheme {
                setContent {
                    //HomeScreen(navController = rememberNavController())
                    MainScreen(updateViewModel, authViewModel)
                }
            }
        }
    }
}

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(
                    context,
                    "Notification permission not granted. Enable it from app settings",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Composable
fun MainScreen(
    updateViewModel: InAppUpdateViewModel,
    authViewModel: AuthViewModel
) {

    val signedUser = authViewModel.signedInUser.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        authViewModel.getSignedInUser()
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
        Screens.Home.route
    )

    val topAppBarTv = listOf(
        Screens.Tv.route
    )

    // Get current back stack entry
    val navBackStackEntry = navController.currentBackStackEntryAsState().value

    // Check if the current destination is in the list of screens to show the bottom bar
    val showBottomBar = navBackStackEntry?.destination?.route in bottomNavScreens
    val showTopBar = navBackStackEntry?.destination?.route in topAppBarScreens
    val showTvTopBAr = navBackStackEntry?.destination?.route in topAppBarTv

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
            } else if (showTvTopBAr) {
                CommonToolbar(
                    onSearchClick = {
                        navController.navigate(Screens.SearchTv.route)
                    }
                )
            }

        }
    ) { innerPadding ->
        val user = signedUser.value
        NavHost(
            navController = navController,
            startDestination = if (user?.uid == null) Screens.Login.route else Screens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(
                route = Screens.Login.route
            ) {
                LoginScreen(navController, onSignInSuccess = {

                })
            }

            composable(route = Screens.Home.route) {
                RequestNotificationPermission()
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

            composable(route = Screens.SearchTv.route) {
                SearchTvScreen(navController)
            }

            composable(
                route = Screens.SearchTvResult.route,
                arguments = listOf(
                    navArgument("query") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query")
                SearchTvScreenResult(query, navController)
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