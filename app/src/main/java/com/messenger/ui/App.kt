package com.messenger.ui

import android.app.Application
import android.content.Context
import com.messenger.di.AppModule
import com.messenger.di.CacheModule
import com.messenger.di.RemoteModule
import com.messenger.di.ViewModelModule

import com.messenger.presentation.service.FirebaseService
import com.messenger.ui.core.friends.FriendRequestsFragment
import com.messenger.ui.core.friends.FriendsFragment
import com.messenger.ui.core.friends.FriendsListFragment
import com.messenger.ui.fragment.LoginFragment
import com.messenger.ui.fragment.TabsFragment
import com.messenger.ui.fragment.RegisterFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
    }

    private fun initAppComponent() {
       appComponent = DaggerAppComponent.factory().create(this)
    }
}

@Singleton
@Component(modules = [AppModule::class, CacheModule::class, RemoteModule::class, ViewModelModule::class])
interface AppComponent {

    //activities
    fun inject(appActivity: AppActivity)

    //fragments
    fun inject(fragment: LoginFragment)
    fun inject(fragment: TabsFragment)
    fun inject(fragment: RegisterFragment)
    fun inject(fragment: FriendRequestsFragment)
    fun inject(fragment: FriendsFragment)
    fun inject(fragment: FriendsListFragment)

    //services
    fun inject(service: FirebaseService)

    @Component.Factory
    interface AppComponentFactory{

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

}