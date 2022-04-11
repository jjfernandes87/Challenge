package paixao.leonardo.marvel.heroes.domain.models

sealed class PagingStatus {
    data class Find(val value: Map<String, Int>) : PagingStatus()
    data class Refresh(val value: Map<String, Int>) : PagingStatus()
    object END : PagingStatus()
}
