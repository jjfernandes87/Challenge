package br.com.mouzinho.marvelapp.view.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.interactor.character.GetCharacters
import br.com.mouzinho.domain.interactor.character.ReloadCharacters
import br.com.mouzinho.domain.interactor.character.SearchCharacters
import br.com.mouzinho.domain.interactor.character.UpdateFavorite
import br.com.mouzinho.domain.resources.StringResources
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class CharactersViewModel @ViewModelInject constructor(
    private val getCharacters: GetCharacters,
    private val updateFavorite: UpdateFavorite,
    private val reload: ReloadCharacters,
    private val searchCharacters: SearchCharacters,
    private val schedulerProvider: SchedulerProvider,
    private val strings: StringResources
) : ViewModel() {
    val stateObservable: Observable<CharactersViewState> by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<CharactersViewState>() }
    private val disposables = CompositeDisposable()

    fun loadCharacters(pageSize: Int = 20) {
        getCharacters(pageSize)
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { statePublisher.onNext(CharactersViewState.Loading) }
            .subscribe(::onCharactersReceived, ::onError)
            .addTo(disposables)
    }

    fun reloadCharacters() {
        reload()
    }

    fun search(text: String) {
        searchCharacters(text)
    }

    fun updateCharacterFromFavorites(character: MarvelCharacter) {
        updateFavorite(character)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribeBy { state ->
                when (state) {
                    UpdateFavorite.State.SAVED -> onFavoriteSaved(character)
                    UpdateFavorite.State.REMOVED -> onFavoriteRemoved(character)
                    else -> onUpdateFavoriteError()
                }
            }
            .addTo(disposables)
    }

    private fun onFavoriteSaved(character: MarvelCharacter) {
        statePublisher.onNext(CharactersViewState.FavoriteSaved(character.copy(isFavorite = true)))
    }

    private fun onFavoriteRemoved(character: MarvelCharacter) {
        statePublisher.onNext(CharactersViewState.FavoriteRemoved(character.copy(isFavorite = false)))
    }

    private fun onUpdateFavoriteError() {
        statePublisher.onNext(CharactersViewState.FavoriteUpdateError(strings.updateFavoriteError))
    }

    private fun onCharactersReceived(pagedList: PagedList<MarvelCharacter>) {
        statePublisher.onNext(CharactersViewState.CharactersLoaded(pagedList))
    }

    private fun onCharactersReload(pagedList: PagedList<MarvelCharacter>) {
        statePublisher.onNext(CharactersViewState.CharactersLoaded(pagedList))
    }

    private fun onError(throwable: Throwable) {
        statePublisher.onNext(CharactersViewState.Error(throwable.message ?: strings.genericErrorMessage))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}