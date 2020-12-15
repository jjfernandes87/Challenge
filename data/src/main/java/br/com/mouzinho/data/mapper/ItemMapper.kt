package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.entity.ApiItem
import br.com.mouzinho.domain.entity.character.Item
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class ItemMapper @Inject constructor() : Mapper<ApiItem, Item> {

    override fun transform(input: ApiItem) = Item(
        name = input.name ?: "",
        resourceURI = input.resourceURI ?: "",
        type = input.type
    )
}