package com.manoelsrs.marvelchallenge.repository.local.comic.mappers

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.local.comic.entity.ComicDto

object ComicMapper {

    fun toData(comicsDto: List<ComicDto>): List<Data> {
        return comicsDto.map { Data(it.id, it.title, it.photo, it.photoExtension) }
    }

    fun toComicsDto(comics: List<Data>): List<ComicDto> {
        return comics.map { ComicDto(it.id, it.title, it.photo, it.photoExtension) }
    }
}