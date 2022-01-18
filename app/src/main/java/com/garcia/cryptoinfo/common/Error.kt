package com.garcia.cryptoinfo.common

import com.garcia.cryptoinfo.R

data class Error(
    val message: String? = null,
    val resourceId: Int = R.string.generic_error,
)
