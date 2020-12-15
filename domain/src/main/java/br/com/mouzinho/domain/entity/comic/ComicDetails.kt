package br.com.mouzinho.domain.entity.comic

import br.com.mouzinho.domain.entity.character.Thumbnail

data class ComicDetails(
    val id: Long,
    val title: String,
    val thumbnail: Thumbnail
)