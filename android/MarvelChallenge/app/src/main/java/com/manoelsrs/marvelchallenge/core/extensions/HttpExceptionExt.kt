package com.manoelsrs.marvelchallenge.core.extensions

import com.manoelsrs.marvelchallenge.R
import retrofit2.HttpException

fun HttpException.friendlyMessage() = when (code()) {
    in 400..499 -> R.string._400_499
    in 500..599 -> R.string._500
    else -> R.string.common_error_message
}