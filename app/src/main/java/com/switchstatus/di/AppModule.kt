package com.switchstatus.di

import com.switchstatus.App
import com.switchstatus.data.local.LocalData
import com.switchstatus.utils.Network
import com.switchstatus.utils.NetworkConnectivity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideLocalRepository(): LocalData {
        return LocalData()
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(): NetworkConnectivity {
        return Network(App.context)
    }
}
