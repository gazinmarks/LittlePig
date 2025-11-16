package br.com.littlepig

import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.repository.user.IUserRepository
import br.com.littlepig.domain.login.LoginUseCase
import br.com.littlepig.domain.login.LoginUseCase.Companion.KEY_USER_TOKEN
import br.com.littlepig.preferences.IDataStorePreferences
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class LoginUseCaseTest {
    private val repository = mockk<IUserRepository>()
    private val dataStore = mockk<IDataStorePreferences>(relaxed = true)
    private val useCase = LoginUseCase(repository, dataStore)

    @Test
    fun `Should throw exception when fields is invalid`() = runTest {
        val fields = listOf("")

        val result = useCase.invoke(fields)

        assert(result.isFailure)
    }

    @Test
    fun `Should login when fields is valid`() = runTest {
        val fields = listOf("teste", "teste@email.com")
        val user = mockk<UserLoginResponse>(relaxed = true)

        coEvery { repository.login(any()) } returns user

        val result = useCase.invoke(fields)

        Assert.assertEquals(Result.success(user), result)
    }

    @Test
    fun `Should save token when is valid`() = runTest {
        val fields = listOf("teste", "teste@email.com")
        val user = mockk<UserLoginResponse>(relaxed = true)
        val slot = slot<String>()

        coEvery { repository.login(any()) } returns user
        every { user.token } returns "token"

        useCase.invoke(fields)

        coVerify(exactly = 1) {
            dataStore.write(KEY_USER_TOKEN, capture(slot))
        }
        Assert.assertEquals("token", slot.captured)
    }

    @Test
    fun `Should not save token when is invalid`() = runTest {
        val fields = listOf("teste", "teste@email.com")
        val user = mockk<UserLoginResponse>(relaxed = true)

        coEvery { repository.login(any()) } returns user
        every { user.token } returns ""

        useCase.invoke(fields)

        coVerify(exactly = 0) {
            dataStore.write(KEY_USER_TOKEN, any())
        }
    }

    @Test
    fun `Should throw exception when login is not successfull`() = runTest {
        val fields = listOf("teste", "teste")

        coEvery { repository.login(any()) } throws Exception()

        val result = useCase.invoke(fields)

        assert(result.isFailure)
    }
}