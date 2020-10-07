package com.elitizamaty.sqliteapp.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.elitizamaty.sqliteapp.R
import com.elitizamaty.sqliteapp.helpers.DatabaseHelper

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        var databaseHelper = DatabaseHelper(this)
        var bookList = databaseHelper.getBookList()
        Log.e("ListViewActivity", "BookList ${bookList[0].bookName}")
    }
}