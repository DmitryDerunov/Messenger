package com.messenger.domain.account

import com.messenger.domain.interactor.UseCase
import com.messenger.domain.type.Either
import com.messenger.domain.type.Failure
import com.messenger.domain.type.None
import javax.inject.Inject

class Logout @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<None, None>(){

    override suspend fun run(params: None): Either<Failure, None> = accountRepository.logout()
}