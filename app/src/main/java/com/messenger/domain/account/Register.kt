package com.messenger.domain.account

import com.messenger.domain.interactor.UseCase
import com.messenger.domain.type.Either
import com.messenger.domain.type.None
import com.messenger.domain.type.exception.Failure
import javax.inject.Inject

class Register @Inject constructor(
    private val repository: AccountRepository
): UseCase<None, Register.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> = repository.register(params.email, params.name, params.password)

    data class Params(val email: String, val name: String, val password: String)
}