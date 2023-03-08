package com.messenger.cache

import android.content.SharedPreferences
import com.messenger.domain.account.AccountEntity
import com.messenger.domain.type.Either
import com.messenger.domain.type.None
import com.messenger.domain.type.Failure
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(private val prefs: SharedPreferences) {
    companion object {
        const val ACCOUNT_TOKEN = "account_token"
        const val ACCOUNT_ID = "account_id"
        const val ACCOUNT_NAME = "account_name"
        const val ACCOUNT_EMAIL = "account_email"
        const val ACCOUNT_STATUS = "account_status"
        const val ACCOUNT_DATE = "account_date"
        const val ACCOUNT_IMAGE = "account_image"
    }

    fun saveToken(token: String): Either<Failure, None> {
        prefs.edit().apply {
            putString(ACCOUNT_TOKEN, token)
        }.apply()

        return Either.Right(None())
    }

    fun getToken(): Either<Failure, String> {
        return Either.Right(prefs.getString(ACCOUNT_TOKEN, "") ?: "")
    }


    fun saveAccount(account: AccountEntity): Either<Failure, None> {
        prefs.edit().apply {
            putLong(ACCOUNT_ID, account.id)
            putString(ACCOUNT_NAME, account.name)
            putString(ACCOUNT_EMAIL, account.email)
            putString(ACCOUNT_TOKEN, account.token)
            putString(ACCOUNT_STATUS, account.status)
            putLong(ACCOUNT_DATE, account.userDate)
            putString(ACCOUNT_IMAGE, account.image)
        }.apply()

        return Either.Right(None())
    }


    fun getAccount(): Either<Failure, AccountEntity> {
        val id = prefs.getLong(ACCOUNT_ID, 0)

        if (id == 0L) {
            return Either.Left(Failure.NoSavedAccountsError)
        }

        val account = AccountEntity(
            prefs.getLong(ACCOUNT_ID, 0),
            prefs.getString(ACCOUNT_NAME, "")?: "",
            prefs.getString(ACCOUNT_EMAIL, "")?: "",
            prefs.getString(ACCOUNT_TOKEN, "")?: "",
            prefs.getString(ACCOUNT_STATUS, "")?: "",
            prefs.getLong(ACCOUNT_DATE, 0),
            prefs.getString(ACCOUNT_IMAGE, "")?: ""
        )

        return Either.Right(account)
    }

    fun removeAccount(): Either<Failure, None> {
        prefs.edit().apply {
            remove(ACCOUNT_ID)
            remove(ACCOUNT_NAME)
            remove(ACCOUNT_EMAIL)
            remove(ACCOUNT_STATUS)
            remove(ACCOUNT_DATE)
            remove(ACCOUNT_IMAGE)
        }.apply()

        return Either.Right(None())
    }

    fun containsAnyAccount(): Boolean {
        val id = prefs.getLong(ACCOUNT_ID, 0)
        return id != 0L
    }
}