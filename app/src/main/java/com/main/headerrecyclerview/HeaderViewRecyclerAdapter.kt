package com.main.headerrecyclerview

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup



/**
 * Created by changfeng on 2017/6/12.
 */
class HeaderViewRecyclerAdapter(headerViews:ArrayList<View>?, footerViews:ArrayList<View>?, adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mHeaderViews:ArrayList<View> ?
    val mFooterViews:ArrayList<View> ?= footerViews
    var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> = adapter

    //init用于在类声明时就定义了构造（主构造），在他之后用于对主构造的参数的初始化，如上也可直接初始化
    init {
        if (headerViews == null){
            mHeaderViews = ArrayList()
        }else {
            mHeaderViews = headerViews
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder?, p1: Int) {

        val headerNum = getHeaderCount()
        if (p1 < headerNum){
            return
        }
        val adjPos = p1 - headerNum
        if (adjPos < mAdapter.itemCount){
            mAdapter.onBindViewHolder(p0,adjPos)
            return
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): RecyclerView.ViewHolder {

        if (p1 == RecyclerView.INVALID_TYPE){
            return HeaderViewHolder(mHeaderViews?.get(0))
        }else if (p1 == RecyclerView.INVALID_TYPE-1){
            return HeaderViewHolder(mFooterViews?.get(0))
        }
        return mAdapter.onCreateViewHolder(p0,p1)
    }

    override fun getItemCount(): Int {
        return mAdapter.itemCount + getFooterCount() + getHeaderCount()
    }

    override fun getItemViewType(position: Int): Int {
        val headerNum = getHeaderCount()
        //头部
        if (position<headerNum){
            return RecyclerView.INVALID_TYPE
        }
        val adjPos = position - headerNum
        if (adjPos < mAdapter.itemCount){
            return mAdapter.getItemViewType(adjPos)
        }
        //尾部
        return RecyclerView.INVALID_TYPE - 1
    }

    fun getHeaderCount():Int = mHeaderViews?.size as Int

    fun getFooterCount():Int = mFooterViews?.size as Int

    private class HeaderViewHolder(view: View?) : ViewHolder(view)
}