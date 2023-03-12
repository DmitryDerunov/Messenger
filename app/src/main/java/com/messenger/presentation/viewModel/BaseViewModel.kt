package com.messenger.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messenger.domain.type.Failure
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    protected val _failureData: MutableLiveData<Failure> = MutableLiveData()
    val failureData: LiveData<Failure>
        get() = _failureData

    open suspend fun loadContent(){

    }

    fun refreshContent() = viewModelScope.launch {
        _isRefreshing.value = true
        loadContent()
        _isRefreshing.value = false
    }

    protected fun handleFailure(failure: Failure) {
        _isLoading.value = false
        _failureData.value = failure
    }
}