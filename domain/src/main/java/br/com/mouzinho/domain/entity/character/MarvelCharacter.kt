package br.com.mouzinho.domain.entity.character

data class MarvelCharacter(
    val comics: Comics?,
    val description: String?,
    val id: Int,
    val name: String,
    val resourceURI: String,
    val series: Series?,
    val thumbnail: Thumbnail?,
)