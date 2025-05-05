package com.malabar.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malabar.core.failure.Failure
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _failure = MutableSharedFlow<Failure>()
    val failure = _failure.asSharedFlow()

    protected fun handleFailure(failure: Failure) {
        viewModelScope.launch {
            _failure.emit(failure)
        }
    }
}