package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.entity.character.ApiComics
import br.com.mouzinho.data.entity.character.ApiItem
import br.com.mouzinho.domain.entity.character.Comics
import br.com.mouzinho.domain.entity.character.Item
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class ComicsMapper @Inject constructor(
    private val itemMapper: Mapper<ApiItem, Item>
) : Mapper<ApiComics, Comics> {

    override fun transform(input: ApiComics) = Comics(
        available = input.available,
        collectionURI = input.collectionURI,
        items = input.items?.let(itemMapper::transform),
        returned = input.returned,
    )
}