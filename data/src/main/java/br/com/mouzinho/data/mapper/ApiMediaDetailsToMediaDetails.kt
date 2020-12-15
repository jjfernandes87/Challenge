package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.entity.ApiMediaDetails
import br.com.mouzinho.data.entity.ApiThumbnail
import br.com.mouzinho.domain.entity.character.Thumbnail
import br.com.mouzinho.domain.entity.comic.MediaDetails
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class ApiMediaDetailsToMediaDetails @Inject constructor(
    private val mapper: Mapper<ApiThumbnail, Thumbnail>
) : Mapper<ApiMediaDetails, MediaDetails> {

    override fun transform(input: ApiMediaDetails) = MediaDetails(
        id = input.id,
        title = input.title,
        thumbnail = input.thumbnail.let(mapper::transform)
    )
}