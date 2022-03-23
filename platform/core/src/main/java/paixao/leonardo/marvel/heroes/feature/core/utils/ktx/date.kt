package paixao.leonardo.marvel.heroes.feature.core.utils.ktx

import okhttp3.internal.UTC
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

fun String.toDate(format: String = DEFAULT_DATE_FORMAT, timeZone: TimeZone = UTC): Date? =
    getDateFormat(format, timeZone)
        .parse(this)

fun Date.toFormattedString(format: String = DEFAULT_DATE_FORMAT, timeZone: TimeZone = UTC): String =
    getDateFormat(format, timeZone)
        .format(this)

private fun getDateFormat(format: String, timeZone: TimeZone): DateFormat =
    SimpleDateFormat(format, Locale.getDefault())
        .also { it.timeZone = timeZone }
