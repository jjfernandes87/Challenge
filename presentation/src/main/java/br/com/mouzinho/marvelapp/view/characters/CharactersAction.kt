package br.com.mouzinho.marvelapp.view.characters

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter

sealed class CharactersAction {
    data class SaveAsFavorite(val character: MarvelCharacter) : CharactersAction()
    data class RemoveFromFavorites(val character: MarvelCharacter) : CharactersAction()
    data class Reload(val characters: PagedList<MarvelCharacter>) : CharactersAction()
}