package com.messenger.remote.account

import com.messenger.domain.account.AccountEntity
import com.messenger.remote.core.BaseResponse

class AuthResponse(
    success: Int,
    message: String,
    val user: AccountEntity
) : BaseResponse(success, message) {
}