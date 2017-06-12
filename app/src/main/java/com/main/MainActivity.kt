package com.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.main.headerrecyclerview.WrapRecyclerView

class MainActivity : AppCompatActivity() {

    var listView:ListView?=null
    private var recyclerView:WrapRecyclerView ?= null
    private val datas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        recyclerView = findViewById(R.id.recycler_view) as WrapRecyclerView
        val header:TextView = TextView(this)
        header.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150)
        header.gravity = Gravity.CENTER
        header.text = "header"
        header.setOnClickListener { Toast.makeText(this@MainActivity,"haha",Toast.LENGTH_SHORT).show() }
        recyclerView?.addHeaderView(header)
        val footer:TextView = TextView(this)
        footer.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100)
        footer.gravity = Gravity.CENTER
        footer.text = "footer"
        recyclerView?.addFooterView(footer)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = MyAdapter(datas)
    }

    fun initData(){
//        for (i in 0..20){
//            datas.add("item "+i)
//        }
        (0..50).mapTo(datas) { "item "+ it }
    }
}
