package com.switchstatus.di

import com.switchstatus.ui.component.details.DetailsActivity
import com.switchstatus.ui.component.login.LoginActivity
import com.switchstatus.ui.component.recipes.RecipesListActivity
import com.switchstatus.ui.component.splash.SplashActivity
import com.switchstatus.ui.component.switches.SwitchesListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeHomeActivity(): RecipesListActivity

    @ContributesAndroidInjector()
    abstract fun contributeDetailsActivity(): DetailsActivity

    @ContributesAndroidInjector()
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector()
    abstract fun contributeSwitchListActivity(): SwitchesListActivity

}
