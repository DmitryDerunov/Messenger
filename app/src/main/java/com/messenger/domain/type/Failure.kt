package com.messenger.domain.type

sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()
    object AuthError : Failure()
    object TokenError : Failure()
    object EmailAlreadyExistError : Failure()
    object NoSavedAccountsError : Failure()
}