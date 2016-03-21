package com.example.ktzhihudaily.data

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by xingzhijian on 2016/3/9.
 */
interface ZhihuDailyService {
    @GET("news/latest")
    fun getLatestNews(): Observable<DataRespository.NewsData>
    @GET("news/before/{date}")
    fun getBeforeNews(@Path("date") date:String): Observable<DataRespository.NewsData>
    @GET("news/{id}")
    fun getNewsDetail(@Path("id") id:Int): Observable<DataRespository.NewsDetail>
    @GET("story/{id}/short-comments")
    fun getShortComments(@Path("id") id:Int): Observable<DataRespository.Comments>
    @GET("story/{id}/long-comments")
    fun getLongComments(@Path("id") id:Int): Observable<DataRespository.Comments>
    @GET("story-extra/{id}")
    fun getNewsExtra(@Path("id") id:Int): Observable<DataRespository.NewsExtra>
}