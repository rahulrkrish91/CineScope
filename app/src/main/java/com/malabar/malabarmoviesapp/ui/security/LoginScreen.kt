package com.malabar.malabarmoviesapp.ui.security

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseUser
import com.malabar.core.AnalyticsHelper
import com.malabar.core.R
import com.malabar.malabarmoviesapp.navigation.Screens
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel(),
    onSignInSuccess: (FirebaseUser?) -> Unit
) {

    val signInState by authViewModel.signInState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(false) }



    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_lottie))
            LottieAnimation(
                composition,
                isPlaying = true,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Text(
                text = stringResource(R.string.login),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally)
            )


            OutlinedButton(
                onClick = {
                    AnalyticsHelper.logEvent(
                        name = "LoginScreen_Google_Click",
                        params = mapOf("Login Click" to "Initiate")
                    )
                    isLoading = true
                    authViewModel.launchGoogleSignIn()

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.google),
                        contentDescription = null, // Decorative icon, no need for description
                        modifier = Modifier.size(20.dp) // Adjust icon size as needed
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Adjust space between icon and text

                    Text(text = "Sign in with Google")
                }
            }

            when (signInState) {
                is SignInState.Error -> {
                    val errorMessage = (signInState as SignInState.Error).message
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Sign-in Error: $errorMessage")
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                SignInState.Idle -> {

                }

                SignInState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is SignInState.Success -> {
                    val user = (signInState as SignInState.Success).user
                    // Trigger the success callback
                    LaunchedEffect(user) {
                        onSignInSuccess(user)
                    }
                    if (user?.uid != null) {
                        navController.navigate(Screens.Home.route)
                    }
                }
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)
                )
            }

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        onSignInSuccess = { Unit }
    )
    CircularProgressIndicator()
    // LoginScreen(rememberNavController(), launcher = ActivityResultLauncher<IntentSenderRequest>, oneTapClient = SignInClient.create())
}