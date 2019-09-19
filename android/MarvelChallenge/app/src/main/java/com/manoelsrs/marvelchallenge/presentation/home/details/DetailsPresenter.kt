package com.manoelsrs.marvelchallenge.presentation.home.details

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character
import com.squareup.picasso.Picasso

class DetailsPresenter(
    private val contract: DetailsContract
) : BasePresenter() {

    var character: Character? = null
        set(value) {
            field = value
            notifyChange()
        }

    fun onCreate(character: Character) {
        this.character = character
        loadPhoto(character)
    }

    private fun loadPhoto(character: Character) {
        Picasso.get()
            .load("${character.photo}/landscape_incredible.${character.photoExtension}")
            .into(contract.getPhotoView())
    }
}