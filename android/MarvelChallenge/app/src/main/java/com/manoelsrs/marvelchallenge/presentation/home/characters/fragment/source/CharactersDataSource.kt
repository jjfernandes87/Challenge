package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.source

import androidx.paging.PositionalDataSource
import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersDataSource(
    private val presenter: BasePresenter,
    private val getCharacters: GetCharacters
) : PositionalDataSource<Character>() {

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<Character>
    ) {
        getCharacters.execute(offset = params.requestedStartPosition)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { callback.onResult(it, 0) },
                { /** TODO: error handling */ }
            ).also { presenter.addDisposable(it) }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Character>) {
        getCharacters.execute(offset = params.loadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { callback.onResult(it) },
                {}
            ).also { presenter.addDisposable(it) }
    }
}