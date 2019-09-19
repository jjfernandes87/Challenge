package com.manoelsrs.marvelchallenge.presentation.home.details

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.GetComics
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(
    private val contract: DetailsContract,
    private val getComics: GetComics
) : BasePresenter() {

    var character: Character? = null
        set(value) {
            field = value
            notifyChange()
        }

    fun onCreate(character: Character) {
        this.character = character
        loadPhoto(character)
        if (character.hasComics) getComics(character.id)
    }

    private fun loadPhoto(character: Character) {
        Picasso.get()
            .load("${character.photo}/landscape_incredible.${character.photoExtension}")
            .into(contract.getPhotoView())
    }

    private fun getComics(characterId: Int) {
        getComics.execute(characterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contract.setComics(it) },
                {
                    it
                }
            ).also { addDisposable(it) }
    }
}