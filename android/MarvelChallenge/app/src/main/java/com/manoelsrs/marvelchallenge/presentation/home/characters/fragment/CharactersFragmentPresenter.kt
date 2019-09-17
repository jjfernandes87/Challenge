package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersFragmentPresenter(
    private val contract: CharactersFragmentContract,
    private val getCharacters: GetCharacters
) : BasePresenter() {

    fun onCreate() {
        getCharacters.execute(offset = 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contract.updateCharacters(it) },
                {}
            ).also { addDisposable(it) }
    }
}