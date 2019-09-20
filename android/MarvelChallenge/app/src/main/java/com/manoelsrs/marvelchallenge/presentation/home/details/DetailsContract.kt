package com.manoelsrs.marvelchallenge.presentation.home.details

import android.widget.ImageView
import com.manoelsrs.marvelchallenge.model.Data
import io.reactivex.Observable

interface DetailsContract {
    fun getPhotoView(): ImageView
    fun setComics(content: List<Data>)
    fun setSeries(content: List<Data>)
    fun showLoading()
    fun stopLoading()
    fun askToSaveFavorite()
    fun askToDeleteFavorite()
    fun getFavoriteListener(): Observable<Unit>
    fun getDeleteFavoriteListener(): Observable<Unit>
    fun showError(message: Int)
    fun finish()
}