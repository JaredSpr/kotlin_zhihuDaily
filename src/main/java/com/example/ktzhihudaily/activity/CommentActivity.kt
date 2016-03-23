package com.example.ktzhihudaily.activity

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import com.example.ktzhihudaily.R
import com.example.ktzhihudaily.Utils.RxBus
import com.example.ktzhihudaily.adapter.CommentsAdapter
import com.example.ktzhihudaily.data.DataRespository
import com.example.ktzhihudaily.event.RxEvents
import kotlinx.android.synthetic.main.activity_comments.*
import rx.functions.Action1

/**
 * Created by xingzhijian on 2016/3/18.
 */
class CommentActivity : SlideBackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        toolBar.setNavigationOnClickListener{ NavUtils.navigateUpFromSameTask(this)}
        toolBar.title = DataRespository?.newsExtra?.comments.toString() + getString(R.string.comment)
//        RxBus.getDefault()?.toObservable()?.subscribe(object: Action1<Any> {
        //            override fun call(p0: Any?) {
        //                if(p0 is RxEvents.ShortCommentsEvent)
        //                    initList()
        //            }
        //        })
        RxBus.getDefault()?.toObservable()?.subscribe({p0->
            if(p0 is RxEvents.ShortCommentsEvent)
                initList()
        })
    }
    fun initList(){
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        val adapter:CommentsAdapter = CommentsAdapter(this)
//        adapter.headerView = LayoutInflater.from(this).inflate(R.layout.view_list_header, null, false)
        recycler_view.adapter = adapter
    }
}