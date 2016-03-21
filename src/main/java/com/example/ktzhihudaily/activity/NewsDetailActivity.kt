package com.example.ktzhihudaily.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import com.example.ktzhihudaily.R
import com.example.ktzhihudaily.Utils.RxBus
import com.example.ktzhihudaily.data.DataRespository
import com.example.ktzhihudaily.event.RxEvents
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_detail.*
import rx.functions.Action1

/**
 * Created by xingzhijian on 2016/3/15.
 */
class NewsDetailActivity : SlideBackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setNewsExtra()
        initWebView()
        toolBar.setNavigationOnClickListener{NavUtils.navigateUpFromSameTask(this)}
        btn_comments.setOnClickListener {
            DataRespository.getLongComments()
            val intent:Intent = Intent(this, CommentActivity::class.java)
            startActivity(intent)
        }
        RxBus.getDefault()?.toObservable()?.subscribe(object: Action1<Any> {
            override fun call(p0: Any?) {
                if (p0 is RxEvents.NewsExtraEvent)
                    setNewsExtra()
            }
        })
    }
    fun initWebView(){
        collapsing_toolbar.title = DataRespository.newsDetail?.title
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.expandedToolbar_TitleText)
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.collapsedToolbar_TitleText)
        Picasso.with(this).load(DataRespository.newsDetail?.image).into(titleImg)
        val html:String = "<html><head><link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\"/></head><body>${DataRespository.newsDetail?.body}</body></html>"
        webView.loadDataWithBaseURL("http://daily.zhihu.com/", html, "text/html", "utf-8", null)
    }
    fun setNewsExtra(){
        comments.text = DataRespository.newsExtra?.comments.toString() + getString(R.string.comment)
    }
}