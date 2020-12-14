package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.database.entity.DbFavoriteCharacter
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class DbFavoriteCharacterToFavoriteCharacterMapper @Inject constructor() : Mapper<DbFavoriteCharacter, FavoriteCharacter> {

    override fun transform(input: DbFavoriteCharacter) = FavoriteCharacter(
        id = input.id,
        name = input.name,
        landscapeThumbnailUrl = input.landscapeThumbnailUrl
    )
}