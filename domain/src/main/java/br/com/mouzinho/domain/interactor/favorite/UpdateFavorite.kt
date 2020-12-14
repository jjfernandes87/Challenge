package br.com.mouzinho.domain.interactor.favorite

import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.repository.favorite.FavoritesMarvelCharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class UpdateFavorite @Inject constructor(
    private val repository: FavoritesMarvelCharacterRepository
) {

    operator fun invoke(character: MarvelCharacter): Single<State> {
        return repository.getFavoriteById(character.id)
            .flatMap { list ->
                list.firstOrNull()?.let {
                    repository.removeFromFavorites(character).map { State.REMOVED }
                } ?: run {
                    repository.saveAsFavorite(character).map { State.SAVED }
                }
            }
            .onErrorReturnItem(State.ERROR)
    }

    enum class State {
        SAVED, REMOVED, ERROR
    }
}