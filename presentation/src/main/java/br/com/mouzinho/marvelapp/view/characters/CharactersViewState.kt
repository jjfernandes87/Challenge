package br.com.mouzinho.marvelapp.view.characters

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter

data class CharactersViewState(
    val loading: Boolean = true,
    val characters: PagedList<MarvelCharacter>? = null,
    val hasError: Boolean = false,
    val reloaded: Boolean = false,
    val errorMessage: String? = null
)