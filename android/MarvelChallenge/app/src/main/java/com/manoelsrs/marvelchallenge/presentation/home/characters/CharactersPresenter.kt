package com.manoelsrs.marvelchallenge.presentation.home.characters

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class CharactersPresenter(
    private val contract: CharactersContract
) : BasePresenter() {

    var search: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    fun onCreate() {
        contract.configureTabs()
    }
}