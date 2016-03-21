package com.example.ktzhihudaily.Utils

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

/**
 * Created by xingzhijian on 2016/3/10.
 */
class RxBus {
    companion object{
        @Volatile private var defaultInstance:RxBus? = null
        fun getDefault(): RxBus? {
            if (defaultInstance == null) {
                synchronized(RxBus::class.java) {
                    if (defaultInstance == null) {
                        defaultInstance = RxBus()
                    }
                }
            }
            return defaultInstance
        }
    }
    private val _bus: Subject<Any, Any> = SerializedSubject<Any, Any>(PublishSubject.create())
    fun send(o:Any){
        _bus.onNext(o)
    }
    fun toObservable(): Observable<Any> {
        return _bus
    }
}