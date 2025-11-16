package br.com.littlepig.di

import br.com.littlepig.data.repository.transaction.ITransactionRepository
import br.com.littlepig.data.repository.transaction.TransactionRepository
import br.com.littlepig.data.repository.user.IUserRepository
import br.com.littlepig.data.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsUserRepository(repository: UserRepository): IUserRepository

    @Binds
    fun bindsTransactionRepository(transactionRepository: TransactionRepository): ITransactionRepository
}