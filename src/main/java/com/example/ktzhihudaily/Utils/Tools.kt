package com.example.ktzhihudaily.Utils

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xingzhijian on 2016/3/11.
 */

fun modifyDate(dateIn:String, day:Int):String{
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMdd")
    val calendar : Calendar = Calendar.getInstance()
    val date = sdf.parse(dateIn)
    calendar.time = date
    calendar.roll(Calendar.DAY_OF_YEAR, day)
    return sdf.format(calendar.time)
}

//Use compose() for the RxJava chain usages
//    final static Observable.Transformer schedulersTransformer = new  Observable.Transformer() {
//        @Override public Object call(Object observable) {
//            return ((Observable)  observable).subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread());
//        }
//    };
//
//    public static <T> Observable.Transformer<T, T> applySchedulers() {
//        return (Observable.Transformer<T, T>) schedulersTransformer;
//    }

var schedulersTransformer:Observable.Transformer<Any, Any> = Observable.Transformer { observable ->
    (observable as Observable<Any>).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> applySchedulers(): Observable.Transformer<T, T>{
    return schedulersTransformer as Observable.Transformer<T, T>
}