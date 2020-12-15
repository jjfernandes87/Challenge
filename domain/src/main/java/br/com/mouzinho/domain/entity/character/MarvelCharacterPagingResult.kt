package br.com.mouzinho.domain.entity.character

import androidx.paging.PagedList

sealed class MarvelCharacterPagingResult {
    data class Created(val list: PagedList<MarvelCharacter>) : MarvelCharacterPagingResult()
    data class Error(val error: Throwable) : MarvelCharacterPagingResult()
    data class Loading(val isLoading: Boolean) : MarvelCharacterPagingResult()
    data class ListCondition(val isEmpty: Boolean) : MarvelCharacterPagingResult()
}