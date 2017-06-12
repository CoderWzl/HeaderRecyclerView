package com.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by changfeng on 2017/6/12.
 */
class MyAdapter(datas:ArrayList<String>): RecyclerView.Adapter<MyAdapter.MyVH>() {

    val datas:ArrayList<String> = datas

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): MyVH {
        val view = LayoutInflater.from(p0?.context).inflate(R.layout.item,p0,false)
        return MyVH(view)
    }

    override fun onBindViewHolder(p0: MyVH?, p1: Int) {
        p0?.text?.text = datas[p1]
    }


    class MyVH(itemView: View):RecyclerView.ViewHolder(itemView){
        val text = itemView.findViewById(R.id.text) as TextView
    }
}
