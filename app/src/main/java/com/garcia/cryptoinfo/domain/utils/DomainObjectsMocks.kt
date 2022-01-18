package com.garcia.cryptoinfo.domain.utils

import com.garcia.cryptoinfo.domain.model.Coin
import com.garcia.cryptoinfo.domain.model.CoinDetail

object DomainObjectsMocks {

    fun getCoinDetail(id: String): CoinDetail{
        return CoinDetail(
            coinId = id,
            name = "CryptoTest",
            description = "Test",
            symbol = "TST",
            rank = 10,
            isActive = true,
            isNew = true,
            type = "",
            tags = listOf(),
            team = listOf(),
        )
    }

    fun getCoins(): List<Coin>{
        return listOf(getCoin())
    }

    fun getCoin(): Coin{
        return Coin(
            id = "20",
            isActive = false,
            isNew = false,
            name = "",
            rank = 0,
            symbol = "",
            type = "",
        )
    }

}