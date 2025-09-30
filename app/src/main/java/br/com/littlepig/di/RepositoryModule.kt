package br.com.littlepig.di

import br.com.littlepig.data.repository.IUserRepository
import br.com.littlepig.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsUserRepository(repository: UserRepository): IUserRepository
}