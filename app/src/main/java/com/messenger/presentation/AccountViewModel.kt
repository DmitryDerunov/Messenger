package com.messenger.presentation

import androidx.lifecycle.MutableLiveData
import com.messenger.domain.account.Register
import com.messenger.domain.type.None
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val registerUseCase : Register
) : BaseViewModel() {

    val registerData:MutableLiveData<None> = MutableLiveData()

    fun register(email: String, name: String, password: String) {
        registerUseCase(Register.Params(email, name, password)) { it.either(::handleFailure, ::handleRegister) }
    }

    private fun handleRegister(none: None) {
        this.registerData.value = none
    }

    override fun onCleared() {
        super.onCleared()
        registerUseCase.unsubscribe()
    }
}