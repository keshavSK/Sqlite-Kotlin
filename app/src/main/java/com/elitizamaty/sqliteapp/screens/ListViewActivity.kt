package com.elitizamaty.sqliteapp.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elitizamaty.sqliteapp.R
import com.elitizamaty.sqliteapp.adapter.BookListAdapter
import com.elitizamaty.sqliteapp.helpers.DatabaseHelper
import kotlinx.android.synthetic.main.activity_list_view.*

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        var databaseHelper = DatabaseHelper(this)
        var bookList = databaseHelper.getBookList()
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        var adapter = BookListAdapter(this, bookList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}