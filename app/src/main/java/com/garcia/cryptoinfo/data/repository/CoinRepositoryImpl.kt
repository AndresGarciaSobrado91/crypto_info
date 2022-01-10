package com.garcia.cryptoinfo.data.repository

import com.garcia.cryptoinfo.data.remote.client.CoinPaprikaApi
import com.garcia.cryptoinfo.data.remote.dto.toCoin
import com.garcia.cryptoinfo.data.remote.dto.toCoinDetail
import com.garcia.cryptoinfo.data.remote.network.ResponseHandler
import com.garcia.cryptoinfo.domain.model.Coin
import com.garcia.cryptoinfo.domain.model.CoinDetail
import com.garcia.cryptoinfo.common.ResultWrapper
import com.garcia.cryptoinfo.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi,
    private val responseHandler: ResponseHandler
) : CoinRepository {

    override suspend fun getCoins(): ResultWrapper<List<Coin>> {
        return responseHandler {
            api.getCoins().map { coinDto -> coinDto.toCoin() }
        }
    }

    override suspend fun getCoinById(coinId: String): ResultWrapper<CoinDetail> {
        return responseHandler {
            api.getCoinById(coinId).toCoinDetail()
        }
    }
}