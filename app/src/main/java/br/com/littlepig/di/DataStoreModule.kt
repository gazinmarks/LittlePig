package br.com.littlepig.di

import br.com.littlepig.preferences.DataStorePreferences
import br.com.littlepig.preferences.IDataStorePreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {
    @Binds
    fun provideDataStoreModule(dataStore: DataStorePreferences): IDataStorePreferences
}