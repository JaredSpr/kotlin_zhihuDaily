package com.example.ktzhihudaily.adapter

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.ktzhihudaily.R
import com.example.ktzhihudaily.data.DataRespository
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xingzhijian on 2016/3/18.
 */
class CommentsAdapter(cont: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER_TYPE_LONG:Int = 1
    val HEADER_TYPE_SHORT:Int = 2
    val context:Context = cont
    val r: Resources = context.resources
    var shortSize:Int = DataRespository.shortComments?.comments?.size!!
    var longSize:Int = DataRespository.longComments?.comments?.size!!
    var headerView:View = LayoutInflater.from(context).inflate(R.layout.view_list_header, null, false)

    override fun getItemCount(): Int {
        var count:Int = 0
        if(longSize>0)
            count += longSize + 1
        if(shortSize>0)
            count += shortSize + 1
        return count
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder?, p1: Int) {
        var dataSource:DataRespository.Comments? = null
        var realPos = 0

//        if((longSize>0 && shortSize>0 && (p1==0 || p1==longSize + 1)) ||
//                    (longSize==0 && shortSize>0 && p1==0) || (longSize>0 && shortSize==0 && p1==0) ||
//                    (longSize==0 && shortSize==0))
//        return
        if(getItemViewType(p1) == HEADER_TYPE_LONG) {
            val headerText:TextView = (p0?.itemView?.findViewById(R.id.text) as TextView)
            headerText.text = longSize.toString() + "条长评"
            return
        }
        else if(getItemViewType(p1) == HEADER_TYPE_SHORT){
            val headerText:TextView = (p0?.itemView?.findViewById(R.id.text) as TextView)
            headerText.text = shortSize.toString() + "条短评"
            return
        }
        else if(p1<=longSize && longSize>0) {
            dataSource = DataRespository.longComments!!
            realPos = p1 - 1
        }
        else if(p1>longSize && longSize>0){
            dataSource = DataRespository.shortComments!!
            realPos = p1 - 2 - longSize
        }
        else if(longSize == 0){
            dataSource = DataRespository.shortComments!!
            realPos = p1 - 1
        }
        val commentContext: DataRespository.CommentItem? = dataSource?.comments?.get(realPos)
        val authorView: TextView = (p0?.itemView?.findViewById(R.id.author) as TextView)
        authorView.text = commentContext?.author
        val imageView: ImageView = (p0?.itemView?.findViewById(R.id.avatar) as ImageView)
        Picasso.with(context).load(commentContext?.avatar).into(imageView)
        val content: TextView = (p0?.itemView?.findViewById(R.id.content) as TextView)
        content.text = commentContext?.content
        if (content.text.length == 0) {
            content.text = commentContext?.reply_to?.content
        }
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = TimeZone.getTimeZone("GMT+8")
        val time: TextView = (p0?.itemView?.findViewById(R.id.time) as TextView)
        time.text = sdf.format(Date(commentContext?.time!! * 1000))
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): RecyclerView.ViewHolder? {
        if(p1 == HEADER_TYPE_LONG || p1 == HEADER_TYPE_SHORT)
            return HeaderHolder(p1, LayoutInflater.from(context).inflate(R.layout.view_list_header, null, false))
        else
            return CommentHolder(LayoutInflater.from(context).inflate(R.layout.comment_item, null, false))
    }

    override fun getItemViewType(position: Int): Int {
        if(longSize>0 && position==0)
            return HEADER_TYPE_LONG
        else if((shortSize >0 && position == longSize + 1 && longSize>0) || (longSize == 0 && position == 0))
            return HEADER_TYPE_SHORT
        else
            return super.getItemViewType(position)
    }

    inner class CommentHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init{
            val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.bottomMargin = r.getDimensionPixelSize(R.dimen.DIMEN_12PX)
            lp.leftMargin = r.getDimensionPixelSize(R.dimen.DIMEN_12PX)
            lp.rightMargin = r.getDimensionPixelSize(R.dimen.DIMEN_12PX)
            itemView.layoutParams = lp
        }
    }
    class HeaderHolder(type:Int, itemView:View):RecyclerView.ViewHolder(itemView){
    }
}