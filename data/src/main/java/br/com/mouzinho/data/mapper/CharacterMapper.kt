package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.entity.character.ApiCharacter
import br.com.mouzinho.data.entity.character.ApiComics
import br.com.mouzinho.data.entity.character.ApiSeries
import br.com.mouzinho.data.entity.character.ApiThumbnail
import br.com.mouzinho.domain.entity.character.Character
import br.com.mouzinho.domain.entity.character.Comics
import br.com.mouzinho.domain.entity.character.Series
import br.com.mouzinho.domain.entity.character.Thumbnail
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class CharacterMapper @Inject constructor(
    private val comicsMapper: Mapper<ApiComics, Comics>,
    private val seriesMapper: Mapper<ApiSeries, Series>,
    private val thumbnailMapper: Mapper<ApiThumbnail, Thumbnail>
) : Mapper<ApiCharacter, Character> {

    override fun transform(input: ApiCharacter) = Character(
        comics = input.comics?.let(comicsMapper::transform),
        description = input.description,
        id = input.id ?: System.currentTimeMillis().toInt(),
        name = input.name ?: "",
        resourceURI = input.resourceURI ?: "",
        series = input.series?.let(seriesMapper::transform),
        thumbnail = input.thumbnail?.let(thumbnailMapper::transform),
    )
}