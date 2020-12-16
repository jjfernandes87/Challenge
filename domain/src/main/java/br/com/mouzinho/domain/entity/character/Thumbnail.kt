package br.com.mouzinho.domain.entity.character

import java.io.Serializable

data class Thumbnail(val extension: String, val path: String) : Serializable {

    val landscapeMediumUrl get() = "${path}/${SIZE_LANDSCAPE_MEDIUM}.${extension}"
    val landscapeXLargeUrl get() = "${path}/${SIZE_LANDSCAPE_XLARGE}.${extension}"
    val portraitMediumUrl get() = "${path}/${SIZE_PORTRAIT_MEDIUM}.${extension}"
    val portraitSmallUrl get() = "${path}/${SIZE_PORTRAIT_SMALL}.${extension}"

    companion object {
        const val SIZE_PORTRAIT_SMALL = "portrait_small"
        const val SIZE_PORTRAIT_MEDIUM = "portrait_medium"
        const val SIZE_LANDSCAPE_MEDIUM = "landscape_medium"
        const val SIZE_LANDSCAPE_XLARGE = "landscape_xlarge"
    }
}