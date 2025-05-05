package com.malabar.malabarmoviesapp.domain.repository.auth

import arrow.core.Either
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.malabar.core.failure.Failure
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): FirebaseUser?
}

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun signInWithGoogle(idToken: String): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return try {
            firebaseAuth.signInWithCredential(credential).await().user
        } catch (e: Exception) {
            null
        }
    }

}