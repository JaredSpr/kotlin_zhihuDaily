package com.example.ktzhihudaily.activity

import android.app.Activity
import android.view.MotionEvent
import android.view.VelocityTracker
import com.example.ktzhihudaily.R

/**
 * Created by xingzhijian on 2016/3/17.
 */
open class SlideBackActivity : Activity() {
    var xDown:Float = 0F
    var yDown:Float = 0F
    var xMove:Float = 0F
    var yMove:Float = 0F
    val XSPEED_MIN:Int = 1000
    val XDISTANCE_MIN:Int = 150
    val YDISTANCE_MAX:Int = 200
    var velocityTracker:VelocityTracker? = null
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        createVelocityTracker(event!!)
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                xDown = event.rawX
                yDown = event.rawY
            }
            MotionEvent.ACTION_MOVE->{
                xMove = event.rawX
                yMove = event.rawY
                var distanceX:Int = (xMove - xDown).toInt()
                var distanceY:Int = (yMove - yDown).toInt()
                var xSpeed:Int = getScrollVelocity()
                if(distanceX>XDISTANCE_MIN && distanceY<YDISTANCE_MAX && xSpeed > XSPEED_MIN) {
                    finish()
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            }
            MotionEvent.ACTION_UP->{
                recycleVelocityTracker()
            }
        }
        return super.dispatchTouchEvent(event)
    }
    fun createVelocityTracker(event:MotionEvent){
        if(velocityTracker == null)
            velocityTracker = VelocityTracker.obtain()
        velocityTracker?.addMovement(event)
    }
    fun recycleVelocityTracker(){
        velocityTracker?.recycle()
        velocityTracker = null
    }
    fun getScrollVelocity():Int{
        velocityTracker?.computeCurrentVelocity(1000)
        var velocity:Int? = velocityTracker?.xVelocity?.toInt()
        return Math.abs(velocity!!)
    }
}