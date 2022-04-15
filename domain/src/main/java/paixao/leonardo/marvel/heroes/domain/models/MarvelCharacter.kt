package paixao.leonardo.marvel.heroes.domain.models

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String,
    val isFavorite: Boolean = false,
    val position: Int
) : java.io.Serializable
