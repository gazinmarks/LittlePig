package br.com.littlepig.di

import br.com.littlepig.domain.usecase.login.ILoginUseCase
import br.com.littlepig.domain.usecase.login.LoginUseCase
import br.com.littlepig.domain.usecase.register.IRegisterUseCase
import br.com.littlepig.domain.usecase.register.RegisterUseCase
import br.com.littlepig.domain.usecase.transactions.ITransactionsUseCase
import br.com.littlepig.domain.usecase.transactions.TransactionsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    fun provideRegisterUseCase(registerUseCase: RegisterUseCase): IRegisterUseCase

    @Binds
    fun provideLoginUseCase(loginUseCase: LoginUseCase): ILoginUseCase

    @Binds
    fun provideTransactionsUseCase(transactionsUseCase: TransactionsUseCase): ITransactionsUseCase
}