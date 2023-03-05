package com.messenger.presentation.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.messenger.domain.account.UpdateToken
import com.messenger.ui.App
import javax.inject.Inject

class FirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var updateToken: UpdateToken

    override fun onCreate() {
        super.onCreate()
        App.appComponent.inject(this)
    }

    override fun onMessageReceived(message: RemoteMessage) {

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}