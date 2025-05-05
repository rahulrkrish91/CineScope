package com.malabar.malabarmoviesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malabar.malabarmoviesapp.domain.interactors.in_app_update.GetCheckAndStartUpdateUseCase
import kotlinx.coroutines.launch

class InAppUpdateViewModel(
    private val getCheckAndStartUpdateUseCase: GetCheckAndStartUpdateUseCase
): ViewModel() {

    fun checkAndStartUpdate() {
        viewModelScope.launch {
            getCheckAndStartUpdateUseCase()
        }
    }
}