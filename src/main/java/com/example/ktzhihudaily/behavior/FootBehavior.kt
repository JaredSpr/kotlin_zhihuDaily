package com.example.ktzhihudaily.behavior

import android.animation.Animator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Interpolator

/**
 * Created by JaredX on 2016/3/17.
 */
class FootBehavior(context:Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(){
    var sinceDirectionChange:Int = 0

    val interpolator: Interpolator = FastOutSlowInInterpolator()

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return (nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        if (dy > 0 && sinceDirectionChange < 0 || dy < 0 && sinceDirectionChange > 0) {
            child?.animate()?.cancel();
            sinceDirectionChange = 0;
        }
        sinceDirectionChange += dy;
//        if (sinceDirectionChange > child!!.getHeight() && child.getVisibility() == View.VISIBLE) {
//            hide(child);
//        } else if (sinceDirectionChange < 0 && child.getVisibility() == View.GONE) {
//            show(child);
//        }
        if (dy > 0 && child!!.getVisibility() == View.VISIBLE) {
            hide(child!!);
        } else if (dy < 0 && child!!.getVisibility() == View.GONE) {
            show(child!!);
        }
    }

    fun hide(view:View){
        val animator: ViewPropertyAnimator = view.animate().translationY(0F).setInterpolator(interpolator).setDuration(200)
        animator.setListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
//                throw UnsupportedOperationException()
            }

            override fun onAnimationRepeat(p0: Animator?) {
//                throw UnsupportedOperationException()
            }

            override fun onAnimationEnd(p0: Animator?) {
                view.visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {
                show(view);
            }
        })
    }

    fun show(view:View){
        val animator: ViewPropertyAnimator = view.animate().translationY(0F).setInterpolator(interpolator).setDuration(200)
        animator.setListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
                //                throw UnsupportedOperationException()
            }

            override fun onAnimationRepeat(p0: Animator?) {
                //                throw UnsupportedOperationException()
            }

            override fun onAnimationEnd(p0: Animator?) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {
                hide(view);
            }
        })
    }
}