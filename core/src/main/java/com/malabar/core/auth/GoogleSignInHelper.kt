package com.malabar.core.auth

import com.google.android.gms.auth.api.identity.BeginSignInRequest

object GoogleSignInHelper {
    fun buildGoogleIdOption(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setServerClientId("625793187063-bfk4uq5s26vog25vj3dtgp0ah1084gog.apps.googleusercontent.com") // From Firebase console
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}