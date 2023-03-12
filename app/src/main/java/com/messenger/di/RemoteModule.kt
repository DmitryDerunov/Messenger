package com.messenger.di

import dagger.Module
import dagger.Provides
import com.messenger.BuildConfig
import com.messenger.data.account.AccountRemote
import com.messenger.data.friends.FriendsRemote
import com.messenger.remote.account.AccountRemoteImpl
import com.messenger.remote.core.Request
import com.messenger.remote.friends.FriendsRemoteImpl
import com.messenger.remote.service.ApiService
import com.messenger.remote.service.ServiceFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = ServiceFactory.makeService(BuildConfig.DEBUG)

    @Singleton
    @Provides
    fun provideAccountRemote(request: Request, apiService: ApiService): AccountRemote {
        return AccountRemoteImpl(request, apiService)
    }

    @Singleton
    @Provides
    fun provideFriendsRemote(request: Request, apiService: ApiService): FriendsRemote {
        return FriendsRemoteImpl(request, apiService)
    }
}