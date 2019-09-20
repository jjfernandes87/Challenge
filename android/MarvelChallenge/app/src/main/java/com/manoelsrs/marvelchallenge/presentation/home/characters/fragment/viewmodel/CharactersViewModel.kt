package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.SaveFavorite
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel(
    private val getCharacters: GetCharacters,
    private val saveFavorite: SaveFavorite
) : ViewModel() {

    private val viewState: MutableLiveData<CharactersViewState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    val characters: LiveData<PagedList<Character>> = getCharacters.execute()

    init {
        updateItems("")
    }

    fun viewState(): LiveData<CharactersViewState> = viewState

    fun loadMoreItems(content: String) {
        //TODO: Handle error
        getCharacters.loadMoreItems(content)
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

    fun updateItems(content: String) {
        getCharacters.updateItems(content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.value = CharactersViewState.Loading(true) }
            .subscribe(
                { viewState.value = CharactersViewState.Loading(false) },
                { viewState.value = CharactersViewState.Error("ERROR!") }
            ).also { compositeDisposable.add(it) }
    }

    fun saveFavorite(character: Character) {
        saveFavorite.execute(character)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .also { compositeDisposable.add(it) }
    }
}