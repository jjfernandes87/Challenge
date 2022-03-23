package paixao.leonardo.marvel.heroes.domain.models

data class Character(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String
)
