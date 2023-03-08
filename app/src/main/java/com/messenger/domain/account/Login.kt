package com.messenger.domain.account

import com.messenger.domain.interactor.UseCase
import com.messenger.domain.type.Either
import com.messenger.domain.type.Failure
import javax.inject.Inject

class Login @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<AccountEntity, Login.Params>() {

    override suspend fun run(params: Params): Either<Failure, AccountEntity> =
        accountRepository.login(params.email, params.password)

    data class Params(val email: String, val password: String)
}