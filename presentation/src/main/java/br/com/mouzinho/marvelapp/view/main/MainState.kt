package br.com.mouzinho.marvelapp.view.main

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter

data class MainState(
    val loading: Boolean = true,
    val characters: PagedList<MarvelCharacter>? = null,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)