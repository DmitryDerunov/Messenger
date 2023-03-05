package com.messenger.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import com.messenger.cache.AccountCacheImpl
import com.messenger.cache.SharedPrefsManager
import com.messenger.data.account.AccountCache
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAccountCache(prefsManager: SharedPrefsManager): AccountCache = AccountCacheImpl(prefsManager)
}