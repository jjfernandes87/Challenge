package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel(
    private val getCharacters: GetCharacters
) : ViewModel() {

    private val viewState: MutableLiveData<CharactersViewState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    val characters: LiveData<PagedList<Character>> = getCharacters.execute()

    init {
        loadMoreItems()
    }

    fun viewState(): LiveData<CharactersViewState> = viewState

    fun loadMoreItems() {
        //TODO: Handle error
        getCharacters.loadMoreItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.value = CharactersViewState.Loading(true) }
            .subscribe(
                { viewState.value = CharactersViewState.Loading(false) },
                { viewState.value = CharactersViewState.Error("ERROR!") }
            ).also { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}