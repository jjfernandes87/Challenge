package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.database.entity.DbFavoriteCharacter
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class MarvelCharacterToDbFavoriteCharacterMapper @Inject constructor(): Mapper<MarvelCharacter, DbFavoriteCharacter> {

    override fun transform(input: MarvelCharacter) = DbFavoriteCharacter(
        id = input.id,
        name = input.name,
        landscapeThumbnailUrl = input.thumbnail?.landscapeMediumUrl
    )
}