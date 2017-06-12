package com.main.headerrecyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

/**
 * Created by changfeng on 2017/6/12.
 */
class WrapRecyclerView(context: Context?,attributeSet: AttributeSet?): RecyclerView(context,attributeSet) {

    val mHeaderViews:ArrayList<View> = ArrayList()
    val mFooterViews:ArrayList<View> = ArrayList()
    var mAdapter:Adapter<ViewHolder> ?= null

    constructor(context: Context?):this(context,null)

    fun addHeaderView(view:View){
        mHeaderViews.add(view)
        if (mAdapter!=null){
            if (mAdapter !is HeaderViewRecyclerAdapter){
                mAdapter = HeaderViewRecyclerAdapter(mHeaderViews,mFooterViews,mAdapter as Adapter)
            }
        }
    }

    fun addFooterView(view:View){
        mFooterViews.add(view)
        if (mAdapter!=null){
            if (mAdapter !is HeaderViewRecyclerAdapter){
                mAdapter = HeaderViewRecyclerAdapter(mHeaderViews,mFooterViews,mAdapter as Adapter)
            }
        }
    }

    override fun setAdapter(adapter: Adapter<ViewHolder>) {
        if (mHeaderViews.size>0||mFooterViews.size>0){
            mAdapter = HeaderViewRecyclerAdapter(mHeaderViews,mFooterViews,adapter)
        }else{
            mAdapter = adapter
        }
        super.setAdapter(mAdapter)
    }

}