package com.messenger.data.account

import com.messenger.domain.account.AccountEntity
import com.messenger.domain.type.Either
import com.messenger.domain.type.None
import com.messenger.domain.type.Failure

interface AccountRemote {
    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None>

    fun login(email: String, password: String, token: String): Either<Failure, AccountEntity>

    fun updateToken(userId: Long, token: String, oldToken: String) : Either<Failure, None>
}