package com.malabar.malabarmoviesapp.domain.interactors.in_app_update

import com.malabar.malabarmoviesapp.domain.data.app_update.InAppUpdateManager

class GetCheckAndStartUpdateUseCase(
    private val updateManager: InAppUpdateManager
) {
    suspend operator fun invoke() {
        if (updateManager.checkForUpdate()) {
            updateManager.startUpdate()
        }
    }
}