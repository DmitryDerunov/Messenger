package com.messenger.remote.account

import com.messenger.data.account.AccountRemote
import com.messenger.domain.account.AccountEntity
import com.messenger.domain.type.Either
import com.messenger.domain.type.None
import com.messenger.domain.type.Failure
import com.messenger.remote.core.Request
import com.messenger.remote.service.ApiService
import javax.inject.Inject

class AccountRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: ApiService
) : AccountRemote {

    override fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None> {
        return request.make(service.register(createRegisterMap(email, name, password, token, userDate))) { None() }
    }

    override fun login(
        email: String,
        password: String,
        token: String
    ): Either<Failure, AccountEntity> {
        return request.make(service.login(createLoginMap(email, password, token))){ it.user}
    }


    override fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, None> {
        return request.make(service.updateToken(createUpdateTokenMap(userId, token, oldToken))) { None() }
    }


    private fun createRegisterMap(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_EMAIL] = email
        map[ApiService.PARAM_NAME] = name
        map[ApiService.PARAM_PASSWORD] = password
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_DATE] = userDate.toString()
        return map
    }

    private fun createLoginMap(email: String, password: String, token: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_EMAIL] = email
        map[ApiService.PARAM_PASSWORD] = password
        map[ApiService.PARAM_TOKEN] = token
        return map
    }

    private fun createUpdateTokenMap(userId: Long, token: String, oldToken: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_USER_ID] = userId.toString()
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_OLD_TOKEN] = oldToken
        return map
    }
}