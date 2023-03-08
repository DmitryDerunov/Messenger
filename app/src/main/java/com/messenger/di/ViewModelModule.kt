package com.messenger.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Binds
import dagger.multibindings.IntoMap
import com.messenger.presentation.viewModel.AccountViewModel
import com.messenger.presentation.viewModel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    @Binds
    abstract fun bindAccountViewModel(impl: AccountViewModel): ViewModel
}