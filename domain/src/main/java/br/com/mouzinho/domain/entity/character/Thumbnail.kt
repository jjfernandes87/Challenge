package br.com.mouzinho.domain.entity.character

data class Thumbnail(val extension: String, val path: String) {
    companion object {
        const val SIZE_PORTRAIT_SMALL = "portrait_small"
        const val SIZE_PORTRAIT_MEDIUM = "portrait_medium"
        const val SIZE_LANDSCAPE_MEDIUM = "landscape_medium"
    }
}