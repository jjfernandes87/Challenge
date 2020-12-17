package com.manoelsrs.marvelchallenge.presentation.home.details.adapters

import com.manoelsrs.marvelchallenge.model.Data

data class DataModel(private val data: Data) : DataModelContract {
    override val raw: Any = data
    override val title: String = data.title
    override val photo: String = data.photo
    override val photoExtension: String = data.photoExtension
}