package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.entity.ApiItem
import br.com.mouzinho.data.entity.ApiSeries
import br.com.mouzinho.domain.entity.character.Item
import br.com.mouzinho.domain.entity.character.Series
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class SeriesMapper @Inject constructor(
    private val itemMapper: Mapper<ApiItem, Item>
) : Mapper<ApiSeries, Series> {

    override fun transform(input: ApiSeries) = Series(
        available = input.available,
        collectionURI = input.collectionURI,
        items = input.items?.let(itemMapper::transform),
        returned = input.returned,
    )
}