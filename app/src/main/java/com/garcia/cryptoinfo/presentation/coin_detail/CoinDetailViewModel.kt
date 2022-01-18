package com.garcia.cryptoinfo.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garcia.cryptoinfo.R
import com.garcia.cryptoinfo.common.Error
import com.garcia.cryptoinfo.common.ResultWrapper
import com.garcia.cryptoinfo.domain.model.CoinDetail
import com.garcia.cryptoinfo.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    data class ViewState(
        val isLoading: Boolean = false,
        val coin: CoinDetail? = null,
        val error: Error? = null,
    )

    private val _state = mutableStateOf (ViewState())
    val state : State<ViewState> = _state

    init {
        savedStateHandle.get<String>("coinId")?.let {
            getCoinDetails(it)
        }
    }

    private fun getCoinDetails(coinId: String){
        _state.value = ViewState(isLoading = true)
        getCoinUseCase(coinId).onEach { result ->
            when(result){
                is ResultWrapper.Error -> {
                    _state.value = ViewState(error = Error(message = result.message))
                }
                ResultWrapper.NetworkError -> {
                    _state.value = ViewState(error = Error(resourceId = R.string.connection_error))
                }
                is ResultWrapper.Success -> {
                    _state.value = ViewState(coin = result.value)
                }
            }
        }.launchIn(viewModelScope)
    }
}