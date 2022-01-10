package com.garcia.cryptoinfo.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garcia.cryptoinfo.common.ResultWrapper
import com.garcia.cryptoinfo.domain.model.Coin
import com.garcia.cryptoinfo.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
) : ViewModel() {

    data class ViewState(
        val isLoading: Boolean = false,
        val coins: List<Coin> = emptyList(),
        val error: String? = null,
    )

    private val _state = mutableStateOf (ViewState())
    val state : State<ViewState> = _state

    init {
        getCoins()
    }

    private fun getCoins(){
        _state.value = ViewState(isLoading = true)
        getCoinsUseCase().onEach { result ->
            when(result){
                is ResultWrapper.Error -> {
                    _state.value = ViewState(error = result.message)
                }
                ResultWrapper.NetworkError -> {
                    _state.value = ViewState(error = "No internet connection.")
                }
                is ResultWrapper.Success -> {
                    _state.value = ViewState(coins = result.value ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}