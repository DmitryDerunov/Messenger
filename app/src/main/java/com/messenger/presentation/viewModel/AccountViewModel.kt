package com.messenger.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.messenger.databinding.FragmentFriendsRequestsBinding
import com.messenger.domain.account.*
import com.messenger.domain.interactor.UseCase
import com.messenger.domain.type.None
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val registerUseCase: Register,
    private val loginUseCase: Login,
    private val logoutUseCase: Logout,
    private val getAccountUseCase: GetAccount
) : BaseViewModel() {


    private val _registerData: MutableLiveData<None> = MutableLiveData()
    val registerData: LiveData<None>
        get() = _registerData

    private val _accountData: MutableLiveData<AccountEntity> = MutableLiveData()
    val accountData: LiveData<AccountEntity>
        get() = _accountData

    private val _logoutData: MutableLiveData<None> = MutableLiveData()
    val logoutData: LiveData<None>
        get() = _logoutData


    fun register(email: String, name: String, password: String) {
        _isLoading.value = true
        registerUseCase(Register.Params(email, name, password)) {
            it.either(
                ::handleFailure,
                ::handleRegister
            )
        }
    }


    fun login(email: String, password: String){
        _isLoading.value = true
        loginUseCase(Login.Params(email, password)){
            it.either(
                ::handleFailure,
                ::handleAccount
            )
        }
    }

    fun getAccount() {
        _isLoading.value = true
        getAccountUseCase(None()) { it.either(::handleFailure, ::handleAccount) }
    }

    fun logout(){
        _isLoading.value = true
        logoutUseCase(None()){it.either(::handleFailure, ::handleLogout)}
    }


    private fun handleRegister(none: None) {
        _isLoading.value = false
        _registerData.value = none
    }

    private fun handleAccount(account: AccountEntity) {
        _isLoading.value = false
        _accountData.value = account
    }

    private fun handleLogout(none: None) {
        _isLoading.value = false
        _logoutData.value = none
    }

    override fun onCleared() {
        super.onCleared()
        registerUseCase.unsubscribe()
        loginUseCase.unsubscribe()
        getAccountUseCase.unsubscribe()
        logoutUseCase.unsubscribe()
    }
}