package com.manoelsrs.marvelchallenge.presentation.home.details

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character
import com.squareup.picasso.Picasso

class DetailsPresenter(
    private val contract: DetailsContract
) : BasePresenter() {

    var name: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    var description: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    fun onCreate(character: Character) {
        description = character.description
        name = character.name.toUpperCase()

        Picasso.get()
            .load("${character.photo}/landscape_incredible.${character.photoExtension}")
            .into(contract.getPhotoView())
    }
}