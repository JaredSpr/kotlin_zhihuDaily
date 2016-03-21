package com.example.ktzhihudaily.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.example.ktzhihudaily.R
import com.example.ktzhihudaily.Utils.RxBus
import com.example.ktzhihudaily.adapter.EndlessRecyclerOnScrollListener
import com.example.ktzhihudaily.adapter.IndexListAdapter
import com.example.ktzhihudaily.data.DataRespository
import com.example.ktzhihudaily.event.RxEvents
import kotlinx.android.synthetic.main.activity_main.*
import rx.functions.Action1

class MainActivity : Activity() {
    val drawerToggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawer_layout, R.string.open_drawer, R.string.close_drawer)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer_layout.setDrawerListener(drawerToggle)
        DataRespository.getLastestNews()
        RxBus.getDefault()?.toObservable()?.subscribe(object:Action1<Any>{
            override fun call(p0: Any?) {
                if(p0 is RxEvents.NewsDoneEvent)
                    initList()
                else if(p0 is RxEvents.NewsBeforeEvent)
                    loadMore()
                else if(p0 is RxEvents.NewsDetailEvent)
                    loadNewsEvent()
            }
        })
        swipe_refresh_layout.setOnRefreshListener ({DataRespository.getLastestNews()})
        swipe_refresh_layout.setColorSchemeColors(Color.BLUE)
        toolBar.title = getString(R.string.app_name)
        toolBar.setTitleTextColor(Color.WHITE)
    }
    fun initList(){
        swipe_refresh_layout.setRefreshing(false)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        val adapter : IndexListAdapter = IndexListAdapter(this)
        adapter.headerView = LayoutInflater.from(this).inflate(R.layout.view_list_header, null, false)
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object : EndlessRecyclerOnScrollListener(recycler_view.layoutManager){
            override fun onLoadMore() {
                DataRespository.getBeforeNews()
            }
        })
    }

    fun loadMore(){
        (recycler_view.adapter as IndexListAdapter).loadMore()
    }

    fun loadNewsEvent(){
        val i: Intent = Intent(this, NewsDetailActivity::class.java)
        startActivity(i)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
