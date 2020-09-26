package com.task.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.ui.ViewModelFactory
import com.task.ui.component.details.DetailsViewModel
import com.task.ui.component.login.LoginViewModel
import com.task.ui.component.recipes.RecipesListViewModel
import com.task.ui.component.splash.SplashViewModel
import com.task.ui.component.switches.SwitchListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Since dagger
 * support multibinding you are free to bind as may ViewModels as you want.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(SwitchListViewModel::class)
    abstract fun bindSwitchViewModel(viewModel: SwitchListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipesListViewModel::class)
    abstract fun bindUserViewModel(viewModel: RecipesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun bindSplashViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}
