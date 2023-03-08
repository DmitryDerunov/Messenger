package com.messenger.data.account

import com.messenger.domain.account.AccountEntity
import com.messenger.domain.type.Either
import com.messenger.domain.type.None
import com.messenger.domain.type.Failure

interface AccountCache {
    fun getToken(): Either<Failure, String>
    fun saveToken(token: String): Either<Failure, None>

    fun logout(): Either<Failure, None>

    fun getCurrentAccount(): Either<Failure, AccountEntity>
    fun saveAccount(account: AccountEntity): Either<Failure, None>
}