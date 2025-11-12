package br.com.littlepig.di

import br.com.littlepig.domain.usecase.balance.BalanceUseCase
import br.com.littlepig.domain.usecase.balance.IBalanceUseCase
import br.com.littlepig.domain.usecase.login.ILoginUseCase
import br.com.littlepig.domain.usecase.login.LoginUseCase
import br.com.littlepig.domain.usecase.register.IRegisterUseCase
import br.com.littlepig.domain.usecase.register.RegisterUseCase
import br.com.littlepig.domain.usecase.transactions.CreateTransactionUseCase
import br.com.littlepig.domain.usecase.transactions.DeleteTransactionUseCase
import br.com.littlepig.domain.usecase.transactions.GetTransactionsUseCase
import br.com.littlepig.domain.usecase.transactions.ICreateTransactionUseCase
import br.com.littlepig.domain.usecase.transactions.IDeleteTransactionUseCase
import br.com.littlepig.domain.usecase.transactions.IGetTransactionsUseCase
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
    fun provideBalanceUseCase(balanceUseCase: BalanceUseCase): IBalanceUseCase

    @Binds
    fun provideTransactionsUseCase(getTransactionsUseCase: GetTransactionsUseCase): IGetTransactionsUseCase

    @Binds
    fun provideDeleteTransactionUseCase(deleteTransactionUseCase: DeleteTransactionUseCase): IDeleteTransactionUseCase

    @Binds
    fun provideCreateTransactionUseCase(createTransactionUseCase: CreateTransactionUseCase): ICreateTransactionUseCase
}