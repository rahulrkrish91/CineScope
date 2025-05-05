package com.malabar.malabarmoviesapp.domain.interactors.auth

import com.google.firebase.auth.FirebaseUser
import com.malabar.malabarmoviesapp.domain.repository.auth.AuthRepository

class SignInWithGoogleUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): FirebaseUser? {
        return authRepository.signInWithGoogle(idToken)
    }
}