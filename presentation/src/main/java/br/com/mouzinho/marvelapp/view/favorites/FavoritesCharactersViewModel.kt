package br.com.mouzinho.marvelapp.view.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import br.com.mouzinho.domain.interactor.favorite.GetFavorites
import br.com.mouzinho.domain.interactor.favorite.UpdateFavorite
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class FavoritesCharactersViewModel @ViewModelInject constructor(
    private val getFavorites: GetFavorites,
    private val updateFavorite: UpdateFavorite,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    val stateObservable by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<FavoritesCharactersViewState>() }
    private val initState = FavoritesCharactersViewState()
    private val disposables = CompositeDisposable()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        getFavorites()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribeBy { statePublisher.onNext(initState.copy(favorites = it)) }
            .addTo(disposables)
    }

    fun updateFavorite(favoriteCharacter: FavoriteCharacter) {
        /*TODO*/
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}