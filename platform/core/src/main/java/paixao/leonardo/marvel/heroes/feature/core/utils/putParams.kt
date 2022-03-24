package paixao.leonardo.marvel.heroes.feature.core.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log

fun Intent.putParams(params: Bundle?) {
    try {
        params?.let(::putExtras)
    } catch (e: Throwable) {
        Log.e("Can't put params in the intent -> $e", e.toString())
    }
}
