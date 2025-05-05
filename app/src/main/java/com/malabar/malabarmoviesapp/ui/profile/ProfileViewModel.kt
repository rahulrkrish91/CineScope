package com.malabar.malabarmoviesapp.ui.profile

import com.google.firebase.auth.FirebaseAuth
import com.malabar.core.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {

    private lateinit var auth: FirebaseAuth
    private var user = auth.currentUser

    fun getProfile(){
        val userMail = user?.email
        val profile = user?.photoUrl

    }

}