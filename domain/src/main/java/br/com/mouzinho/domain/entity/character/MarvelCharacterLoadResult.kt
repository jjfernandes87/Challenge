package br.com.mouzinho.domain.entity.character

import androidx.paging.PagedList

sealed class MarvelCharacterLoadResult {
    data class Created(val list: PagedList<MarvelCharacter>) : MarvelCharacterLoadResult()
    data class Error(val error: Throwable) : MarvelCharacterLoadResult()
    data class Loading(val isLoading: Boolean) : MarvelCharacterLoadResult()
    data class ListCondition(val isEmpty: Boolean) : MarvelCharacterLoadResult()
}