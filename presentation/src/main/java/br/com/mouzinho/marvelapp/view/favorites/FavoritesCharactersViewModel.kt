package br.com.mouzinho.marvelapp.view.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import br.com.mouzinho.domain.interactor.favorite.GetFavorites
import br.com.mouzinho.domain.interactor.favorite.RemoveFromFavorites
import br.com.mouzinho.domain.interactor.favorite.SearchFavorites
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import br.com.mouzinho.marvelapp.view.favorites.FavoritesCharactersViewState.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class FavoritesCharactersViewModel @ViewModelInject constructor(
    private val getFavorites: GetFavorites,
    private val removeFromFavorites: RemoveFromFavorites,
    private val searchFavorites: SearchFavorites,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    val stateObservable: Observable<FavoritesCharactersViewState> by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<FavoritesCharactersViewState>() }
    private val disposables = CompositeDisposable()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        getFavorites()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribeBy { statePublisher.onNext(ShowFavorites(it)) }
            .addTo(disposables)
    }

    fun search(text: String) {
        searchFavorites(text)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribeBy { list ->
                statePublisher.onNext(ShowFavorites(list, fromSearch = true))
            }
            .addTo(disposables)
    }

    fun removeFavorite(favoriteCharacter: FavoriteCharacter) {
        removeFromFavorites(favoriteCharacter)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribeBy { success ->
                if (success) {
                    statePublisher.onNext(ShowRemovedMessage)
                    statePublisher.onNext(ReloadCharacters)
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}