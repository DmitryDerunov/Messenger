package com.messenger.ui

import android.app.Application
import android.content.Context
import com.messenger.di.AppModule
import com.messenger.di.CacheModule
import com.messenger.di.RemoteModule
import com.messenger.di.ViewModelModule

import com.messenger.presentation.service.FirebaseService
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
    //fun inject(activity: RegisterActivity)

    //fragments
    //fun inject(fragment: RegisterFragment)

    //services
    fun inject(service: FirebaseService)

    @Component.Factory
    interface AppComponentFactory{

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

}