package com.garcia.cryptoinfo.di

import com.garcia.cryptoinfo.common.Constants
import com.garcia.cryptoinfo.data.remote.client.CoinPaprikaApi
import com.garcia.cryptoinfo.data.repository.CoinRepositoryImpl
import com.garcia.cryptoinfo.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCoinRepository(
        coinRepositoryImpl: CoinRepositoryImpl
    ): CoinRepository

    companion object{

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun providePaprikaApi(retrofit: Retrofit): CoinPaprikaApi {
            return retrofit.create(CoinPaprikaApi::class.java)
        }
    }
}