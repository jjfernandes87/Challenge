package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetFavorites
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class FavoritesViewModel(
    private val getFavorites: GetFavorites
) : ViewModel() {

    private val viewState: MutableLiveData<CharactersViewState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    var characters: LiveData<PagedList<Character>> = getFavorites.execute()

    fun viewState(): LiveData<CharactersViewState> = viewState

    fun updateItems(content: String) {
        getFavorites.updateItems(content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.value = CharactersViewState.Loading(true) }
            .subscribe(
                {
                    viewState.value = CharactersViewState.Loading(false)
                },
                { viewState.value = CharactersViewState.Error("ERROR!") }
            ).also { compositeDisposable.add(it) }
    }
}