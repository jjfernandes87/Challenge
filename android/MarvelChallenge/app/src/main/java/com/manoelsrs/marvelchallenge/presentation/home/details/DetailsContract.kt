package com.manoelsrs.marvelchallenge.presentation.home.details

import android.widget.ImageView
import com.manoelsrs.marvelchallenge.model.Data

interface DetailsContract {
    fun getPhotoView(): ImageView
    fun setComics(content: List<Data>)
    fun setSeries(content: List<Data>)
}