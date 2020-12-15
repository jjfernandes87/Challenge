package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.entity.ApiComicDetails
import br.com.mouzinho.data.entity.ApiThumbnail
import br.com.mouzinho.domain.entity.character.Thumbnail
import br.com.mouzinho.domain.entity.comic.ComicDetails
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class ApiComicDetailsToComicDetails @Inject constructor(
    private val mapper: Mapper<ApiThumbnail, Thumbnail>
) : Mapper<ApiComicDetails, ComicDetails> {

    override fun transform(input: ApiComicDetails) = ComicDetails(
        id = input.id,
        title = input.title,
        thumbnail = input.thumbnail.let(mapper::transform)
    )
}