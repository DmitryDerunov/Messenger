package com.messenger.presentation

import com.messenger.cache.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
){
    fun userLoggedIn() = sharedPrefsManager.containsAnyAccount()
}