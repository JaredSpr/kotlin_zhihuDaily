package com.example.ktzhihudaily.Utils

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