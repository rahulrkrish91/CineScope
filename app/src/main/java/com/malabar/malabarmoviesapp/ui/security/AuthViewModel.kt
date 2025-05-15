package com.malabar.malabarmoviesapp.ui.security

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.Credential

import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.malabar.malabarmoviesapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    //private val signInWithGoogleUseCase: SignInWithGoogleUseCase
    private val auth: FirebaseAuth,
    private val credentialManager: CredentialManager,
    private val context: Context
) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: MutableStateFlow<SignInState> = _signInState

    private val _signedInUser = MutableStateFlow<FirebaseUser?>(null)
    val signedInUser: MutableStateFlow<FirebaseUser?> = _signedInUser

    init {
        _signInState.value = SignInState.Success(auth.currentUser)
    }

    fun launchGoogleSignIn() {
        _signInState.value = SignInState.Loading

        val googleIsOption = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIsOption)
            .build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )
                handleCredential(result.credential)
            } catch (ex: GetCredentialException) {
                _signInState.value = SignInState.Error(ex.localizedMessage ?: "Sign In failed")
            }
        }
    }

    private fun handleCredential(credential: Credential) {
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        } else {
            _signInState.value = SignInState.Error("Sign-in failed: Invalid Credentials")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        _signInState.value = SignInState.Loading

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _signInState.value = SignInState.Success(auth.currentUser)
                } else {
                    _signInState.value =
                        SignInState.Error(task.exception?.localizedMessage ?: "Sign In failed")
                }
            }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                auth.signOut()
                val clearRequest = ClearCredentialStateRequest()
                credentialManager.clearCredentialState(clearRequest)
                _signInState.value = SignInState.Success(null)
            } catch (ex: Exception) {
                _signInState.value = SignInState.Error("Sign-out failed: ${ex.message}")
            }
        }
    }

    fun getSignedInUser() {
        _signedInUser.value = auth.currentUser
    }

    /*private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    fun onGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            val result = signInWithGoogleUseCase(idToken)
            _user.value = result
        }
    }*/
}

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    data class Success(val user: FirebaseUser?) : SignInState()
    data class Error(val message: String) : SignInState()
}