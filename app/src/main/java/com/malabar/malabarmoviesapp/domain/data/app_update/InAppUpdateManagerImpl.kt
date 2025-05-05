package com.malabar.malabarmoviesapp.domain.data.app_update

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallException
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallErrorCode
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.tasks.await

class InAppUpdateManagerImpl(private val activity: Activity): InAppUpdateManager {

    private val appUpdateManager = AppUpdateManagerFactory.create(activity)

    override suspend fun checkForUpdate(): Boolean {

        /*val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            // ... Handle successful result
        }
        appUpdateInfoTask.addOnFailureListener { exception ->
            if (exception is InstallException) {
                when (exception.errorCode) {
                    InstallErrorCode.ERROR_INSTALL_NOT_ALLOWED -> {
                        // Handle the specific error
                        // Inform the user why the update failed.
                        showUserFriendlyMessage("App update is not allowed right now. Please ensure your device has sufficient battery and storage space, and try again later.")
                    }
                    // Handle other InstallErrorCodes if you wish
                    else -> {
                        // Handle other possible exceptions
                        Log.e("AppUpdate", "An error occurred during the update: ${exception.message}")
                        showUserFriendlyMessage("An error occurred during the update. Please try again later.")
                    }
                }
            } else {
                // Other possible errors
                Log.e("AppUpdate", "An error occurred during the update: ${exception.message}")
                showUserFriendlyMessage("An error occurred during the update. Please try again later.")
            }
        }*/

        val info = appUpdateManager.appUpdateInfo.await()
        return info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
    }

    override fun startUpdate() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    activity,
                    UPDATE_REQUEST_CODE
                )
            }
        }
    }

    companion object {
        const val UPDATE_REQUEST_CODE = 123
    }
}