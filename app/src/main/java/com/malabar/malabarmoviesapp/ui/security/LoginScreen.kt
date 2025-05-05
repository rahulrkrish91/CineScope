package com.malabar.malabarmoviesapp.ui.security

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.malabar.core.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel()
) {

    val user = authViewModel.user.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Text(
                text = stringResource(R.string.login),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = {
                    Text(
                        text = stringResource(R.string.username)
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(
                        text = stringResource(R.string.password)
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            )

            Button(
                onClick = {
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.login)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp, end = 10.dp),
                )
                Text(
                    text = stringResource(R.string.or),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp, end = 10.dp)
                )
            }


            OutlinedButton(
                onClick = {
                    scope.launch {
                        try {
                            val credentialsManager = CredentialManager.create(context)
                            val googleIdOption = GetGoogleIdOption.Builder()
                                .setServerClientId("625793187063-6gfatqtgbvveb7k3mdm5knimtkcekt78.apps.googleusercontent.com")
                                .build()

                            val request = GetCredentialRequest.Builder()
                                .addCredentialOption(googleIdOption)
                                .build()

                            try {
                                val result = credentialsManager.getCredential(context, request)
                                val credentials = result.credential as? GoogleIdTokenCredential
                                val idOption = credentials?.idToken
                                if (idOption != null) {
                                    authViewModel.onGoogleSignIn(idOption)
                                }
                            } catch (ex: GetCredentialException) {
                                Log.d("LoginScreen", "Google Sign-In failed", ex)
                            }
                        } catch (ex: Exception) {
                            Log.d("TAG", "LoginScreen: $ex")
                        }


                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val user = authViewModel.user
                    Image(
                        painter = painterResource(R.drawable.google),
                        contentDescription = null, // Decorative icon, no need for description
                        modifier = Modifier.size(20.dp) // Adjust icon size as needed
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Adjust space between icon and text

                    Text(text = "Google")
                }
            }
        }

        user.let {
            Text("Welcome: ${it.value?.displayName}")
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}