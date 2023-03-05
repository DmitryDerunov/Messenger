package com.messenger.di

import dagger.Module
import dagger.Provides
import com.messenger.data.account.AccountCache
import com.messenger.data.account.AccountRemote
import com.messenger.data.account.AccountRepositoryImpl
import com.messenger.domain.account.AccountRepository
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAccountRepository(remote: AccountRemote, cache: AccountCache): AccountRepository {
        return AccountRepositoryImpl(remote, cache)
    }
}