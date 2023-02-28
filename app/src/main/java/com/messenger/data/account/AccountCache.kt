package com.messenger.data.account

import com.messenger.domain.type.Either
import com.messenger.domain.type.None
import com.messenger.domain.type.exception.Failure

interface AccountCache {
    fun getToken(): Either<Failure, String>
    fun saveToken(token: String): Either<Failure, None>
}