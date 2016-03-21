package com.example.ktzhihudaily.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.support.v7.widget.CardView
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
import java.util.*

/**
 * Created by xingzhijian on 2016/3/9.
 */
class IndexListAdapter(cont: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val context = cont
    val r: Resources = context.resources
    val TYPE_HEADER = 1
    val TYPE_DATE = 2
    var headerView : View? = null
    override fun getItemCount(): Int {
        var count =  DataRespository.newsData?.stories?.count()!!
        if(headerView!=null)
            count++
        return count
    }

    override fun onCreateViewHolder(p0: ViewGroup?, type: Int): RecyclerView.ViewHolder? {
        if((type == TYPE_HEADER || type == TYPE_DATE) && headerView!=null)
            return HeaderViewHolder(headerView!!)
        else
            return CardViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, null, false) as CardView)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder?, position: Int) {
        var listPos = position
        if(headerView!=null)
            listPos --
        if(getItemViewType(position) == TYPE_DATE){
            (p0?.itemView?.findViewById(R.id.text) as TextView).text = getStories()?.get(listPos)?.title
        }
        else if(getItemViewType(position) == TYPE_HEADER){
            (p0?.itemView?.findViewById(R.id.text) as TextView).text = context.getString(R.string.today_news)
        }
        else{
            val viewHolder: CardViewHolder = p0 as CardViewHolder
            val title: TextView = viewHolder.itemView.findViewById(R.id.title) as TextView
            title.text = getStories()?.get(listPos)?.title
            val image: ImageView = viewHolder.itemView.findViewById(R.id.image) as ImageView
            Picasso.with(context).load(getStories()?.get(listPos)?.images?.get(0)).into(image)
            viewHolder.itemView.setOnClickListener({
//                (viewHolder.itemView.findViewById(R.id.title) as TextView).setTextColor(Color.GRAY)
//                notifyItemRangeChanged(position, 1)
                DataRespository.getNewsDetail(getStories()?.get(listPos)?.id!!)
                DataRespository.getNewsExtra(getStories()?.get(listPos)?.id!!)
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position==0 && headerView!=null)
            return TYPE_HEADER
        else if(getStories()?.get(position-1)?.type == Int.MAX_VALUE)
            return TYPE_DATE
        else
            return super.getItemViewType(position)
    }

    fun loadMore(){
        notifyDataSetChanged()
    }

    fun getStories():ArrayList<DataRespository.NewsDataItem>?{
        return DataRespository.newsData?.stories
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init{
            val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, r.getDimensionPixelSize(R.dimen.DIMEN_150PX))
            lp.bottomMargin = r.getDimensionPixelSize(R.dimen.DIMEN_12PX)
            lp.leftMargin = r.getDimensionPixelSize(R.dimen.DIMEN_12PX)
            lp.rightMargin = r.getDimensionPixelSize(R.dimen.DIMEN_12PX)
            itemView.layoutParams = lp
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }
}