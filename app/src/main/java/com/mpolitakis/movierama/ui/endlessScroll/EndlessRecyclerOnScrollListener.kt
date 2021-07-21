package com.mpolitakis.movierama.ui.endlessScroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener(private val mLinearLayoutManager: LinearLayoutManager
) :
    RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 20
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    private var current_page = 1
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount
            <= firstVisibleItem + visibleThreshold
        ) {

            current_page++
            onLoadMore(current_page)
            loading = true
        }
    }

    abstract fun onLoadMore(current_page: Int)
}
