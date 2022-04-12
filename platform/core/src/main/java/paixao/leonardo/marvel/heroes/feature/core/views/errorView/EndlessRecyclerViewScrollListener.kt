package paixao.leonardo.marvel.heroes.feature.core.views.errorView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Adapted from Java version, courtesy by
// https://guides.codepath.com/android/endless-scrolling-with-adapterviews-and-recyclerview

class EndlessRecyclerViewScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val onReached: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private val visibleThreshold = 10 // should ever be PAGE_SIZE / 2
    private val startingPageIndex = 0

    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true

    var locked = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        val totalItemCount = layoutManager.itemCount

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) loading = true
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            loading = true
            if (!locked) onReached.invoke(currentPage)
        }
    }

    fun resetState() {
        currentPage = this.startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }
}
