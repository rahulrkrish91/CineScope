package com.malabar.core

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.annotation.RequiresPermission
import com.google.firebase.analytics.FirebaseAnalytics


object AnalyticsHelper {
    private var firebaseAnalytics: FirebaseAnalytics? = null

    @RequiresPermission(allOf = [Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WAKE_LOCK])
    fun init(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun logEvent(name: String, params: Map<String, Any?> = emptyMap()) {
        val bundle = Bundle().apply {
            for((key, value) in params) {
                when(value){
                   is String->{
                       putString(key, value)
                   }
                }
            }
        }
        firebaseAnalytics?.logEvent(name, bundle)
    }
}