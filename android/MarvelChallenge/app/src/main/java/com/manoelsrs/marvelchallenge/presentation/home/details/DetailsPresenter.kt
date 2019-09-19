package com.manoelsrs.marvelchallenge.presentation.home.details

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character

class DetailsPresenter(
    private val contract: DetailsContract
) : BasePresenter() {

    var description: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    fun onCreate(character: Character) {
        description = character.description
    }
}