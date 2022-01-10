package com.garcia.cryptoinfo.domain.use_case.get_coin

import com.garcia.cryptoinfo.domain.model.CoinDetail
import com.garcia.cryptoinfo.common.ResultWrapper
import com.garcia.cryptoinfo.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(coinId: String): Flow<ResultWrapper<CoinDetail>> = flow {
        emit(repository.getCoinById(coinId))
    }
}