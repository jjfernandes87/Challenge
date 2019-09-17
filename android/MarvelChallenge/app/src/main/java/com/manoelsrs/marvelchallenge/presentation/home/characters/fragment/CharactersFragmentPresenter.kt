package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.source.CharactersDataSourceFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersFragmentPresenter(
    private val contract: CharactersFragmentContract,
    private val getCharacters: GetCharacters
) : BasePresenter() {

    fun onCreate() {
        Observable.fromCallable {
            val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(0)
                .setPageSize(20)
                .build()
            val sourceFactory = CharactersDataSourceFactory(this, getCharacters)
            Pair(sourceFactory, pagedListConfig)
        }
            .flatMap { RxPagedListBuilder(it.first, it.second).buildObservable() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { contract.updateCharacters(it) }
            .also { addDisposable(it) }
    }
}