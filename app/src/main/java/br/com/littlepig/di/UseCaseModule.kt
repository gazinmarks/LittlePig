package br.com.littlepig.di

import br.com.littlepig.domain.login.ILoginUseCase
import br.com.littlepig.domain.login.LoginUseCase
import br.com.littlepig.domain.register.IRegisterUseCase
import br.com.littlepig.domain.register.RegisterUseCase
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
}