package com.garcia.cryptoinfo.presentation.coin_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.garcia.cryptoinfo.R
import com.garcia.cryptoinfo.common.Error
import com.garcia.cryptoinfo.common.ResultWrapper
import com.garcia.cryptoinfo.domain.model.Coin
import com.garcia.cryptoinfo.domain.use_case.get_coins.GetCoinsUseCase
import com.garcia.cryptoinfo.domain.utils.DomainObjectsMocks
import com.garcia.cryptoinfo.utils.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CoinListViewModelTest{

    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CoinListViewModel

    @MockK(relaxed = true)
    lateinit var getCoinsUseCase: GetCoinsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `verify state when getCoinsUseCase returns Success`() {
        // given
        val data = DomainObjectsMocks.getCoins()
        given(ResultWrapper.Success(data))

        // when
        viewModel = CoinListViewModel(getCoinsUseCase)

        // verify
        viewModel.stateLiveData.value shouldBeEqualTo CoinListViewModel.ViewState(
            isLoading = false,
            coins = data,
            error = null
        )
    }

    @Test
    fun `verify state when getCoinsUseCase returns Error`() {
        // given
        val error = ResultWrapper.Error(message = "Error message")
        given(error)

        // when
        viewModel = CoinListViewModel(getCoinsUseCase)

        // verify
        viewModel.stateLiveData.value shouldBeEqualTo CoinListViewModel.ViewState(
            isLoading = false,
            coins = emptyList(),
            error = Error(message = error.message),
        )
    }

    @Test
    fun `verify state when getCoinsUseCase returns NetworkError`() {
        // given
        given(ResultWrapper.NetworkError)

        // when
        viewModel = CoinListViewModel(getCoinsUseCase)

        // verify
        viewModel.stateLiveData.value shouldBeEqualTo CoinListViewModel.ViewState(
            isLoading = false,
            coins = emptyList(),
            error = Error(resourceId = R.string.connection_error),
        )
    }

    private fun given(
        getCoinsResult: ResultWrapper<List<Coin>>
    ){
        coEvery { getCoinsUseCase() } returns flowOf(getCoinsResult)
    }
}