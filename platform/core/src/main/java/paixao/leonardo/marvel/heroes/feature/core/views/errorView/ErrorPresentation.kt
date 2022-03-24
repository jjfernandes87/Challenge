package paixao.leonardo.marvel.heroes.feature.core.views.errorView

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import paixao.leonardo.marvel.heroes.feature.core.R

enum class ErrorPresentation(
    @DrawableRes val imgRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
) {

    CONNECTION_ISSUES(
        imgRes = R.drawable.ic_connection_error,
        titleRes = R.string.error_connection_title,
        subtitleRes = R.string.error_connection_subtitle
    ),

    UNKNOWN_ERROR(
        imgRes = R.drawable.img_success,
        titleRes = R.string.error_unknown_title,
        subtitleRes = R.string.error_unknown_subtitle
    ),

    NOT_FOUND(
        imgRes = R.drawable.ic_common_error,
        titleRes = R.string.error_not_found_title,
        subtitleRes = R.string.error_not_found_subtitle
    ),

    SERIALIZATION_ISSUES(
        imgRes = R.drawable.ic_common_error,
        titleRes = R.string.error_serialization_title,
        subtitleRes = R.string.error_serialization_subtitle
    )
}
