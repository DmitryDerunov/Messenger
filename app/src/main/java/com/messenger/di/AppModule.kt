package com.messenger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import com.messenger.data.account.AccountCache
import com.messenger.data.account.AccountRemote
import com.messenger.data.account.AccountRepositoryImpl
import com.messenger.data.friends.FriendsRemote
import com.messenger.data.friends.FriendsRepositoryImpl
import com.messenger.data.media.MediaRepositoryImpl
import com.messenger.domain.account.AccountRepository
import com.messenger.domain.friends.FriendsRepository
import com.messenger.domain.media.MediaRepository
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAccountRepository(remote: AccountRemote, cache: AccountCache): AccountRepository {
        return AccountRepositoryImpl(remote, cache)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(remote: FriendsRemote, accountCache: AccountCache): FriendsRepository {
        return FriendsRepositoryImpl(accountCache, remote)
    }

    @Provides
    @Singleton
    fun provideMediaRepository(context: Context): MediaRepository {
        return MediaRepositoryImpl(context)
    }
}