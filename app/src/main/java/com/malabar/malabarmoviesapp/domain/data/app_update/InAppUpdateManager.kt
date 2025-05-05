package com.malabar.malabarmoviesapp.domain.data.app_update

interface InAppUpdateManager {
    suspend fun checkForUpdate(): Boolean
    fun startUpdate()
}