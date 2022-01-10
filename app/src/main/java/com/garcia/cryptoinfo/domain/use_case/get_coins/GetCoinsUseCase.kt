package com.garcia.cryptoinfo.domain.use_case.get_coins

import com.garcia.cryptoinfo.common.ResultWrapper
import com.garcia.cryptoinfo.domain.model.Coin
import com.garcia.cryptoinfo.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
){

    operator fun invoke(): Flow<ResultWrapper<List<Coin>>> = flow {
        emit(repository.getCoins())
    }
}