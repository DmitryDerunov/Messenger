package com.messenger.data.account

import com.messenger.domain.type.Either
import com.messenger.domain.type.None
import com.messenger.domain.type.exception.Failure

interface AccountRemote {
    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None>
}