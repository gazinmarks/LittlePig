package br.com.littlepig.domain

interface IRegisterUseCase {
    suspend operator fun invoke(fields: List<String>): Result<Unit>
}