package com.malabar.malabarmoviesapp.ui.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.malabar.malabarmoviesapp.domain.interactors.auth.SignInWithGoogleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    fun onGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            val result = signInWithGoogleUseCase(idToken)
            _user.value = result
        }
    }
}