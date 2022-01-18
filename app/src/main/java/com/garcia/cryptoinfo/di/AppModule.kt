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

/*
* Sometimes a type cannot be constructor-injected. This can happen for multiple reasons. For example,
* you cannot constructor-inject an interface. You also cannot constructor-inject a type that you do not own,
* such as a class from an external library. In these cases, you can provide Hilt with binding information by using Hilt modules.
* */

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /*@Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi, responseHandler: ResponseHandler): CoinRepository {
        return  CoinRepositoryImpl(api,responseHandler)
    }*/

    /* Inject interface instances with @Binds:
    * You cannot annotate interfaces with @Inject since interfaces don’t have constructors.
    * However, if you have a one-to-one mapping between an interface and implementation,
    * you can use @Binds to make an interface injectable. Injecting an interface (rather than a concrete implementation)
    * is always a good practice for several reasons including ease of testing.  For instance,
    * */
    @Binds
    @Singleton
    abstract fun bindCoinRepository(
        coinRepositoryImpl: CoinRepositoryImpl
    ): CoinRepository

    companion object{
        /* Inject instances with @Provides
        * Interfaces are not the only case where you cannot constructor-inject a type.
        * Constructor injection is also not possible if you don't own the class because
        * it comes from an external library (classes like Retrofit, OkHttpClient, or Room databases),
        * or if instances must be created with the builder pattern.
        * */

        @Provides
        @Singleton
        fun providePaprikaApi(): CoinPaprikaApi {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinPaprikaApi::class.java)
        }
        // @Provides are set here as STATIC because we can't mix non-static methods with abstract functions
    }
}