package com.switchstatus.di

import com.switchstatus.TestDataRepository
import com.switchstatus.data.DataRepositorySource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class TestDataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: TestDataRepository): DataRepositorySource
}
