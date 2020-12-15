package br.com.mouzinho.marvelapp.view.characterDetails

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.comic.MediaDetails
import br.com.mouzinho.domain.interactor.character.UpdateFavorite
import br.com.mouzinho.domain.interactor.media.GetMediaDetails
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import br.com.mouzinho.marvelapp.view.characterDetails.CharacterDetailsViewState.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class CharacterDetailsViewModel @ViewModelInject constructor(
    private val getMediaDetails: GetMediaDetails,
    private val updateFavorite: UpdateFavorite,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    val stateObservable: Observable<CharacterDetailsViewState> by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<CharacterDetailsViewState>() }
    private val disposables = CompositeDisposable()

    fun loadMedia(marvelCharacter: MarvelCharacter) {
        Observable
            .zip(
                getComicsObservable(marvelCharacter).subscribeOn(schedulerProvider.io()),
                getSeriesObservable(marvelCharacter).subscribeOn(schedulerProvider.io()),
                { comics, series -> comics to series }
            )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .doOnSubscribe { statePublisher.onNext(ToggleLoading(isLoading = true)) }
            .doAfterTerminate { statePublisher.onNext(ToggleLoading(isLoading = false)) }
            .subscribeBy(
                onNext = { (comics, series) -> onComicsAndSeriesReceived(marvelCharacter, comics, series) },
                onError = { Log.d("<loadExtraMedia>", "loadMedia: $it") }
            )
            .addTo(disposables)
    }

    fun updateFavoriteStatus(marvelCharacter: MarvelCharacter) {
        updateFavorite(marvelCharacter)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribeBy { state ->
                when (state) {
                    UpdateFavorite.State.REMOVED -> statePublisher.onNext(ToggleFavorite(false))
                    UpdateFavorite.State.SAVED -> statePublisher.onNext(ToggleFavorite(true))
                    else -> Unit
                }
            }
            .addTo(disposables)
    }

    private fun getComicsObservable(marvelCharacter: MarvelCharacter): Observable<List<MediaDetails>> {
        return marvelCharacter.comics?.items?.let { items ->
            val count = marvelCharacter.comics?.available ?: 0
            if (count == 0) return Observable.just(emptyList())
            Observable.fromIterable(items)
                .flatMap { item ->
                    getMediaDetails(item.resourceURI).toObservable().subscribeOn(schedulerProvider.io())
                }
                .buffer(count)
        } ?: Observable.empty()
    }

    private fun getSeriesObservable(marvelCharacter: MarvelCharacter): Observable<List<MediaDetails>> {
        return marvelCharacter.series?.items?.let { items ->
            val count = marvelCharacter.comics?.available ?: 0
            if (count == 0) return Observable.just(emptyList())
            Observable.fromIterable(items)
                .flatMap { item ->
                    getMediaDetails(item.resourceURI).toObservable().subscribeOn(schedulerProvider.io())
                }
                .buffer(count)
        } ?: Observable.empty()
    }

    private fun onComicsAndSeriesReceived(
        marvelCharacter: MarvelCharacter,
        comics: List<MediaDetails>,
        series: List<MediaDetails>
    ) {
        statePublisher.onNext(
            ShowDetails(
                headerImageUrl = marvelCharacter.thumbnail?.landscapeXLargeUrl ?: "",
                description = marvelCharacter.description ?: "",
                comics = comics,
                series = series
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}