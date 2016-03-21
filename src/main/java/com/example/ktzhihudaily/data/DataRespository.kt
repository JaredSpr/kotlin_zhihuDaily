package com.example.ktzhihudaily.data

import com.example.ktzhihudaily.Utils.RxBus
import com.example.ktzhihudaily.Utils.modifyDate
import com.example.ktzhihudaily.event.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by xingzhijian on 2016/3/9.
 */
object DataRespository{
    val baseUrl = "http://news-at.zhihu.com/api/4/"
    data class NewsDataItem(val title:String, val images:ArrayList<String>, val type:Int, val ga_prefix:String, val id:Int)
    data class NewsData(var date:String, var stories: ArrayList<NewsDataItem>, val topStories:List<NewsDataItem>)
    data class NewsDetail(val body:String, val image_source:String, val title:String, val image:String, val share_url:String, val js:ArrayList<String>, val css:ArrayList<String>, val id:Int)
    data class NewsExtra(val long_comments:Int, val short_comments:Int, val popularity:Int, val comments:Int)
    data class CommentItem(val author:String, val id:Int, val content:String, val likes:Int, val time:Long, val avatar:String, val reply_to:CommentReply)
    data class Comments(val comments:ArrayList<CommentItem>)
    data class CommentReply(val content:String, val id:Int, val author: String)
    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
    val service: ZhihuDailyService = retrofit.create(ZhihuDailyService::class.java)
    var newsData: NewsData? = null
    var newsDetail:NewsDetail ?= null
    var shortComments:Comments ?= null
    var longComments:Comments ?= null
    var newsExtra:NewsExtra ?= null

    fun getLastestNews(){
        service.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<NewsData> {
                    override fun onNext(p0: NewsData?) {
                        newsData = p0
                        RxBus.Companion.getDefault()?.send(RxEvents.NewsDoneEvent())
                    }

                    override fun onCompleted() {
//                        throw UnsupportedOperationException()
                    }

                    override fun onError(p0: Throwable?) {
                        throw UnsupportedOperationException()
                    }
            })
    }
    fun getBeforeNews(){
        service.getBeforeNews(newsData?.date!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:Observer<NewsData>{
                    override fun onNext(p0: NewsData?) {
                        newsData?.date = p0?.date!!
                        newsData?.stories?.add(NewsDataItem(p0?.date!!, ArrayList(), Int.MAX_VALUE, "", 0))   //keep the position of date
                        p0?.stories?.forEach {newsData?.stories?.add(it)}
                        RxBus.Companion.getDefault()?.send(RxEvents.NewsBeforeEvent())
                    }

                    override fun onCompleted() {
//                        throw UnsupportedOperationException()
                    }

                    override fun onError(p0: Throwable?) {
                        throw UnsupportedOperationException()
                    }
                })
    }
    fun getNewsDetail(id:Int){
        service.getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NewsDetail>{
                    override fun onNext(p0: NewsDetail?) {
                        newsDetail = p0
                        RxBus.Companion.getDefault()?.send(RxEvents.NewsDetailEvent())
                    }
                    override fun onCompleted() {
//                        throw UnsupportedOperationException()
                    }

                    override fun onError(p0: Throwable?) {
                        throw UnsupportedOperationException()
                    }
                })
    }
    fun getNewsExtra(id:Int){
        service.getNewsExtra(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NewsExtra> {
                    override fun onNext(p0: NewsExtra?) {
                        newsExtra = p0
                        RxBus.Companion.getDefault()?.send(RxEvents.NewsExtraEvent())
                    }

                    override fun onCompleted() {
//                        throw UnsupportedOperationException()
                    }

                    override fun onError(p0: Throwable?) {
                        throw UnsupportedOperationException()
                    }
                })
    }
    fun getShortComments(){
        service.getShortComments(newsDetail?.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (object : Observer<Comments>{
                    override fun onNext(p0: Comments?) {
                        shortComments = p0
                        RxBus.Companion.getDefault()?.send(RxEvents.ShortCommentsEvent())
                    }

                    override fun onCompleted() {
//                        throw UnsupportedOperationException()
                    }

                    override fun onError(p0: Throwable?) {
                        throw UnsupportedOperationException()
                    }
                })
    }

    fun getLongComments(){
        service.getLongComments(newsDetail?.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (object : Observer<Comments>{
                    override fun onNext(p0: Comments?) {
                        longComments = p0
                        getShortComments()
                    }

                    override fun onCompleted() {
                        //                        throw UnsupportedOperationException()
                    }

                    override fun onError(p0: Throwable?) {
                        throw UnsupportedOperationException()
                    }
                })
    }
}