package br.com.littlepig

import android.util.Log
import br.com.littlepig.data.model.User
import br.com.littlepig.data.repository.IUserRepository
import br.com.littlepig.domain.register.RegisterUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RegisterUseCaseTest {
    private val repository = mockk<IUserRepository>()
    private val useCase = RegisterUseCase(repository)

    @After
    fun setup() {
        mockkStatic(Log::class)
        every { Log.d(any(), any(), any()) } returns 0
    }

    @Before
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Should throw exception when fields is invalid`() = runTest {
        val fields = listOf("")

        val result = useCase.invoke(fields)

        assertTrue(result.isFailure)
    }

    @Test
    fun `Should register an user when fields is valid`() = runTest {
        val fields = listOf("teste", "teste", "teste@gmail.com")

        val user = mockk<User>()

        coEvery { repository.registerUser(any()) } returns user

        val result = useCase.invoke(fields)

        Assert.assertEquals(Result.success(user), result)
    }

    @Test
    fun `Should throw exception when user not created`() = runTest {
        val fields = listOf("teste", "teste", "teste@gmail.com")

        coEvery { repository.registerUser(any()) } throws Exception()

        val result = useCase.invoke(fields)

        assertTrue(result.isFailure)
    }
}