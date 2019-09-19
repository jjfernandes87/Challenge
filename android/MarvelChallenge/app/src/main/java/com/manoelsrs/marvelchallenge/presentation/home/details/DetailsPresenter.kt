package com.manoelsrs.marvelchallenge.presentation.home.details

import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.GetComics
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.GetSeries
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.SaveFavorite
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(
    private val contract: DetailsContract,
    private val getComics: GetComics,
    private val getSeries: GetSeries,
    private val saveFavorite: SaveFavorite
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
        if (character.hasSeries) getSeries(character.id)
    }

    private fun loadPhoto(character: Character) {
        Picasso.get()
            .load("${character.photo}/landscape_incredible.${character.photoExtension}")
            .error(R.mipmap.ic_launcher)
            .into(contract.getPhotoView())
    }

    private fun getComics(characterId: Int) {
        getComics.execute(characterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contract.setComics(it) },
                { /** todo: error handle */ }
            ).also { addDisposable(it) }
    }

    private fun getSeries(characterId: Int) {
        getSeries.execute(characterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contract.setSeries(it) },
                { /** todo: error handle */ }
            ).also { addDisposable(it) }
    }

    fun loadMoreComics() {
        getComics.loadMore(character!!.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contract.setComics(it) },
                { /** todo: error handle */ }
            ).also { addDisposable(it) }
    }

    fun loadMoreSeries() {
        getSeries.loadMore(character!!.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contract.setSeries(it) },
                { /** todo: error handle */ }
            ).also { addDisposable(it) }
    }

    fun saveFavorite() {
        saveFavorite.execute(character!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .also { addDisposable(it) }
    }
}