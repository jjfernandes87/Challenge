package br.com.mouzinho.domain.entity.character

import java.io.Serializable

data class Item(
    val name: String,
    val resourceURI: String,
    val type: String?
) : Serializable