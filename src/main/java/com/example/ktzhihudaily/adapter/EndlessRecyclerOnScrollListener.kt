package com.example.ktzhihudaily.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by xingzhijian on 2016/3/11.
 */
abstract class EndlessRecyclerOnScrollListener(layoutManager:RecyclerView.LayoutManager) : RecyclerView.OnScrollListener(){
    var visiableItemCount:Int = 0
    var totalItemCount:Int = 0
    var previousTotal:Int = 0
    var loading:Boolean = false
    val linearLayoutManager:RecyclerView.LayoutManager = layoutManager
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visiableItemCount = recyclerView?.childCount!!
        totalItemCount = linearLayoutManager.itemCount
        if(loading){
            if(totalItemCount > previousTotal +1){
                loading = false
            }
        }
        previousTotal = totalItemCount
        if(!loading && totalItemCount > visiableItemCount && (totalItemCount - visiableItemCount) > 2) {
            loading = true
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}