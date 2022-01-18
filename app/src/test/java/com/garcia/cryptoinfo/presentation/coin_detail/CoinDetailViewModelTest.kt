package com.garcia.cryptoinfo.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import com.garcia.cryptoinfo.R
import com.garcia.cryptoinfo.common.Error
import com.garcia.cryptoinfo.common.ResultWrapper
import com.garcia.cryptoinfo.domain.model.CoinDetail
import com.garcia.cryptoinfo.domain.use_case.get_coin.GetCoinUseCase
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
class CoinDetailViewModelTest{

    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    private lateinit var viewModel: CoinDetailViewModel

    @MockK(relaxed = true)
    lateinit var getCoinUseCase: GetCoinUseCase

    lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        savedStateHandle = SavedStateHandle()
        savedStateHandle.set("coinId","123")
    }

    @Test
    fun `verify state when getCoinUseCase returns Success`() {
        // given
        val data = DomainObjectsMocks.getCoinDetail("10")
        given(ResultWrapper.Success(data))

        // when
        viewModel = CoinDetailViewModel(getCoinUseCase, savedStateHandle)

        // verify
        viewModel.state.value shouldBeEqualTo CoinDetailViewModel.ViewState(
            isLoading = false,
            coin = data,
            error = null
        )
    }

    @Test
    fun `verify state when getCoinUseCase returns Error`() {
        // given
        val error = ResultWrapper.Error(message = "Error message")
        given(error)

        // when
        viewModel = CoinDetailViewModel(getCoinUseCase, savedStateHandle)

        // verify
        viewModel.state.value shouldBeEqualTo CoinDetailViewModel.ViewState(
            isLoading = false,
            coin = null,
            error = Error(message = error.message)
        )
    }

    @Test
    fun `verify state when getCoinUseCase returns NetworkError`() {
        // given
        given(ResultWrapper.NetworkError)

        // when
        viewModel = CoinDetailViewModel(getCoinUseCase, savedStateHandle)

        // verify
        viewModel.state.value shouldBeEqualTo CoinDetailViewModel.ViewState(
            isLoading = false,
            coin = null,
            error = Error(resourceId = R.string.connection_error)
        )
    }

    private fun given(
        result: ResultWrapper<CoinDetail>
    ){
        coEvery { getCoinUseCase(any()) } returns flowOf(result)
    }
}