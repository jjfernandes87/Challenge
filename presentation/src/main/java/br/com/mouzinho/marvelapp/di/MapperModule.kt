package br.com.mouzinho.marvelapp.di

import br.com.mouzinho.data.database.entity.DbFavoriteCharacter
import br.com.mouzinho.data.entity.*
import br.com.mouzinho.data.mapper.*
import br.com.mouzinho.domain.entity.character.*
import br.com.mouzinho.domain.entity.comic.ComicDetails
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import br.com.mouzinho.domain.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface MapperModule {

    @Binds
    fun bindCharacterMapper(impl: CharacterMapper): Mapper<ApiMarvelCharacter, MarvelCharacter>

    @Binds
    fun bindSeriesMapper(impl: SeriesMapper): Mapper<ApiSeries, Series>

    @Binds
    fun bindItemMapper(impl: ItemMapper): Mapper<ApiItem, Item>

    @Binds
    fun bindComicsMapper(impl: ComicsMapper): Mapper<ApiComics, Comics>

    @Binds
    fun bindThumbnailMapper(impl: ThumbnailMapper): Mapper<ApiThumbnail, Thumbnail>

    @Binds
    fun bindMarvelCharacterToDbFavoriteCharacterMapper(impl: MarvelCharacterToDbFavoriteCharacterMapper): Mapper<MarvelCharacter, DbFavoriteCharacter>

    @Binds
    fun bindDbFavoriteCharacterToFavoriteCharacterMapper(impl: DbFavoriteCharacterToFavoriteCharacterMapper): Mapper<DbFavoriteCharacter, FavoriteCharacter>

    @Binds
    fun bindFavoriteCharacterToDbFavoriteCharacterMapper(impl: FavoriteCharacterToDbFavoriteCharacterMapper): Mapper<FavoriteCharacter, DbFavoriteCharacter>

    @Binds
    fun bindApiComicDetailsToComicDetailsMapper(impl: ApiComicDetailsToComicDetails): Mapper<ApiComicDetails, ComicDetails>
}