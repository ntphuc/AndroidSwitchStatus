package com.switchstatus.di

import com.switchstatus.data.error.mapper.ErrorMapper
import com.switchstatus.data.error.mapper.ErrorMapperInterface
import com.switchstatus.usecase.errors.ErrorFactory
import com.switchstatus.usecase.errors.ErrorManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

// with @Module we Telling Dagger that, this is a Dagger module
@Module
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorFactory

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperInterface
}
