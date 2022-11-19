package com.manoelsrs.marvelchallenge.presentation.home.details

import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.core.extensions.friendlyMessage
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.*
import com.squareup.picasso.Picasso
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

class DetailsPresenter(
    private val contract: DetailsContract,
    private val getComics: GetComics,
    private val getSeries: GetSeries,
    private val saveFavorite: SaveFavorite,
    private val checkFavorite: CheckFavorite,
    private val deleteFavorite: DeleteFavorite
) : BasePresenter() {

    companion object {
        private const val COMMOM_DESCRIPTION = "No information received!"
    }

    var character: Character? = null
        set(value) {
            field = value
            description = value?.description ?: COMMOM_DESCRIPTION
            notifyChange()
        }

    var description: String = ""
        set(value) {
            field = if (value.isBlank()) COMMOM_DESCRIPTION else value
            notifyChange()
        }

    fun onCreate(character: Character) {
        this.character = character
        fetchLists(character)
        listenFavorite(character)
        listenDeleteFavorite(character)
    }

    private fun fetchLists(character: Character) {
        Single.fromCallable { loadPhoto(character) }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .flatMap { getComics.execute(character.id) }
            .observeOn(AndroidSchedulers.mainThread())
            .map { contract.setComics(it) }
            .observeOn(Schedulers.io())
            .flatMap { getSeries.execute(character.id) }
            .observeOn(AndroidSchedulers.mainThread())
            .map { contract.setSeries(it) }
            .doOnSubscribe { contract.showLoading() }
            .subscribe(
                { contract.stopLoading() },
                {
                    with(contract) {
                        stopLoading()
                        when (it) {
                            is HttpException -> it.friendlyMessage()
                            is TimeoutException -> R.string.timeout
                            is SocketTimeoutException -> R.string.timeout
                            else -> R.string.noConnection
                        }.apply { showError(this) }
                    }
                }
            ).also { addDisposable(it) }
    }

    private fun listenFavorite(character: Character) {
        contract.getFavoriteListener()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .flatMap { saveFavorite.execute(character).toObservable() }
            .subscribe({}, { contract.showError(R.string.common_error_message) })
            .also { addDisposable(it) }
    }

    private fun listenDeleteFavorite(character: Character) {
        contract.getDeleteFavoriteListener()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .flatMap { deleteFavorite.execute(character).toObservable() }
            .subscribe({}, { contract.showError(R.string.common_error_message) })
            .also { addDisposable(it) }
    }

    private fun loadPhoto(character: Character) {
        Picasso.get()
            .load("${character.photo}/landscape_incredible.${character.photoExtension}")
            .error(R.mipmap.ic_launcher)
            .into(contract.getPhotoView())
    }

    fun loadMoreComics() {
        getComics.loadMore(character!!.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contract.setComics(it) },
                { contract.showError(R.string.comics_error_message) }
            ).also { addDisposable(it) }
    }

    fun loadMoreSeries() {
        getSeries.loadMore(character!!.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contract.setSeries(it) },
                { contract.showError(R.string.series_error_message) }
            ).also { addDisposable(it) }
    }

    fun askFavorite() {
        checkFavorite.execute(character!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { if (it) contract.askToDeleteFavorite() else contract.askToSaveFavorite() },
                { contract.showError(R.string.common_error_message) }
            ).also { addDisposable(it) }
    }
}