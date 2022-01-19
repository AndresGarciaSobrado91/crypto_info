package com.garcia.cryptoinfo.data.remote.network

import android.content.res.Resources
import com.garcia.cryptoinfo.R
import com.garcia.cryptoinfo.common.ResultWrapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ResponseHandler @Inject constructor() {

    suspend operator fun <T : Any> invoke(apiCall: suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            val errorMessage = throwable.localizedMessage ?: Resources.getSystem().getString(R.string.unexpected_error_message)
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    ResultWrapper.Error(code, errorMessage)
                }
                else -> {
                    ResultWrapper.Error(null, Resources.getSystem().getString(R.string.unexpected_error_message))
                }
            }
        }
    }
}