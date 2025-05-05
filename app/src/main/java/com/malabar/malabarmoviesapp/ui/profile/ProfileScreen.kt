package com.malabar.malabarmoviesapp.ui.profile

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.malabar.malabarmoviesapp.navigation.Screens

@Composable
fun ProfileScreen(navController: NavController) {

    /*var auth: FirebaseAuth = FirebaseAuth.*//*getInstance()
    var user = auth.currentUser*/
    val context = LocalContext.current
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*AsyncImage(
                model = user?.photoUrl,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .size(100.dp)
            )*/

          /*  user?.email?.let {
                Text(
                    text = it
                )
            }*/

            OutlinedButton(
                onClick = {
                    //auth.signOut()
                    val activity = context as? Activity
                    if (activity != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            activity.finishAffinity()
                        } else {
                            activity.finishAndRemoveTask()
                        }
                    }
                    //navController.navigate(Screens.Login.route)
                },
            ) {
                Text(
                    text = "Sign Out"
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(rememberNavController())
}