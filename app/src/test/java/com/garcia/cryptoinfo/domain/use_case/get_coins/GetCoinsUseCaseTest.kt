package com.garcia.cryptoinfo.domain.use_case.get_coins

import com.garcia.cryptoinfo.common.ResultWrapper
import com.garcia.cryptoinfo.domain.utils.DomainObjectsMocks
import com.garcia.cryptoinfo.domain.model.Coin
import com.garcia.cryptoinfo.domain.repository.CoinRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetCoinsUseCaseTest{

    private lateinit var useCase: GetCoinsUseCase

    @MockK
    internal lateinit var repository: CoinRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetCoinsUseCase(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should call getCoins`() = runBlockingTest {
        // given
        given(ResultWrapper.Success(DomainObjectsMocks.getCoins()))

        // when
        val result = useCase()

        // then
        result.count() shouldBeEqualTo 1
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return ResultWrapperSuccess when getCoins is successful`() = runBlockingTest {
        // given
        val data = DomainObjectsMocks.getCoins()
        given(ResultWrapper.Success(data))

        // when
        val result = useCase()

        // then
        result.first() shouldBeEqualTo ResultWrapper.Success(data)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return ResultWrapperError when getCoins returns error`() = runBlockingTest {
        // given
        given(ResultWrapper.Error())

        // when
        val result = useCase()

        // then
        result.first() shouldBeEqualTo ResultWrapper.Error()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return ResultWrapperNetworkError when getCoins returns networkError`() = runBlockingTest {
        // given
        given(ResultWrapper.NetworkError)

        // when
        val result = useCase()

        // then
        result.first() shouldBeEqualTo ResultWrapper.NetworkError
    }

    private fun given(
        result: ResultWrapper<List<Coin>>
    ){
        coEvery { repository.getCoins() } returns result
    }
}