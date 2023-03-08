package com.messenger.presentation.viewModel

import androidx.lifecycle.MutableLiveData
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

    val registerData: MutableLiveData<None> = MutableLiveData()
    val accountData: MutableLiveData<AccountEntity> = MutableLiveData()
    val logoutData: MutableLiveData<None> = MutableLiveData()

    fun register(email: String, name: String, password: String) {
        registerUseCase(Register.Params(email, name, password)) {
            it.either(
                ::handleFailure,
                ::handleRegister
            )
        }
    }


    fun login(email: String, password: String){
        loginUseCase(Login.Params(email, password)){
            it.either(
                ::handleFailure,
                ::handleAccount
            )
        }
    }

    fun getAccount() {
        getAccountUseCase(None()) { it.either(::handleFailure, ::handleAccount) }
    }

    fun logout(){
        logoutUseCase(None()){it.either(::handleFailure, ::handleLogout)}
    }

    private fun handleRegister(none: None) {
        this.registerData.value = none
    }

    private fun handleAccount(account: AccountEntity) {
        this.accountData.value = account
    }

    private fun handleLogout(none: None) {
        this.logoutData.value = none
    }

    override fun onCleared() {
        super.onCleared()
        registerUseCase.unsubscribe()
        loginUseCase.unsubscribe()
        getAccountUseCase.unsubscribe()
        logoutUseCase.unsubscribe()
    }
}