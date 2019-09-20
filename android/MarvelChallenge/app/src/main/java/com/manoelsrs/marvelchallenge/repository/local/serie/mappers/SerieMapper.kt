package com.manoelsrs.marvelchallenge.repository.local.serie.mappers

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.local.serie.entity.SerieDto

object SerieMapper {

    fun toData(seriesDto: List<SerieDto>): List<Data> {
        return seriesDto.map { Data(it.id, it.title, it.photo, it.photoExtension) }
    }

    fun toSeriesDto(series: List<Data>): List<SerieDto> {
        return series.map { SerieDto(it.id, it.title, it.photo, it.photoExtension) }
    }
}