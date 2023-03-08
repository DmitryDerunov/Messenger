package com.messenger.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.messenger.domain.type.Failure

abstract class BaseViewModel : ViewModel() {

    var failureData: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failureData.value = failure
    }
}