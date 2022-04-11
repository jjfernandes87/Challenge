package paixao.leonardo.marvel.heroes.data

import paixao.leonardo.marvel.heroes.data.models.PagedResponse
import paixao.leonardo.marvel.heroes.domain.models.PagingStatus

class PagingHandler {

    private companion object {
        const val PAGE_SIZE = 20
        const val OFF_SET_KEY = "offset"
    }

    private var _totalItems: Int = 0
    private var _retrievedItems: Int = 0

    fun retrievePageStatus(isRefreshing: Boolean): PagingStatus {
        if (isRefreshing) {
            _retrievedItems = 0
            return PagingStatus.Refresh(mapOf(OFF_SET_KEY to 0))
        }

        val lastItems = _totalItems - _retrievedItems

        val newOffSetValue = when {
            lastItems > PAGE_SIZE -> _retrievedItems + PAGE_SIZE
            lastItems > 0 -> lastItems
            else -> return PagingStatus.END
        }

        return PagingStatus.Find(mapOf(OFF_SET_KEY to newOffSetValue))
    }

    fun <T> updatePagingHandler(response: PagedResponse<T>) {
        response.apply {
            _totalItems = total
            _retrievedItems = offset
        }
    }
}
