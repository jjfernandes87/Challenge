package br.com.mouzinho.marvelapp.view.characterDetails

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.character.Series
import br.com.mouzinho.domain.entity.comic.ComicDetails
import br.com.mouzinho.domain.interactor.comics.GetComicDetails
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import br.com.mouzinho.marvelapp.view.characterDetails.CharacterDetailsViewState.ShowDetails
import br.com.mouzinho.marvelapp.view.characterDetails.CharacterDetailsViewState.ToggleLoading
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class CharacterDetailsViewModel @ViewModelInject constructor(
    private val getComicDetails: GetComicDetails,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    val stateObservable by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<CharacterDetailsViewState>() }

    fun loadComics(marvelCharacter: MarvelCharacter) {
        marvelCharacter.comics?.items?.let { items ->
            Observable.fromIterable(items)
                .flatMap { item ->
                    getComicDetails(item.resourceURI).toObservable().subscribeOn(schedulerProvider.io())
                }
                .buffer(marvelCharacter.comics?.available ?: 0)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .doOnSubscribe { statePublisher.onNext(ToggleLoading(isLoading = true)) }
                .doAfterTerminate { statePublisher.onNext(ToggleLoading(isLoading = false)) }
                .subscribeBy(
                    onNext = { onComicsAndSeriesReceived(marvelCharacter, it, emptyList()) },
                    onError = { Log.d("<ComicDetails>", "loadComics: $it") }
                )
        }
    }

    private fun onComicsAndSeriesReceived(
        marvelCharacter: MarvelCharacter,
        comics: List<ComicDetails>,
        series: List<Series>
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
}