package br.com.mouzinho.data.mapper

import br.com.mouzinho.data.entity.ApiThumbnail
import br.com.mouzinho.domain.entity.character.Thumbnail
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class ThumbnailMapper @Inject constructor() : Mapper<ApiThumbnail, Thumbnail> {

    override fun transform(input: ApiThumbnail) = Thumbnail(
        extension = input.extension ?: "jpg",
        path = input.path?.replace("http", "https") ?: ""
    )
}