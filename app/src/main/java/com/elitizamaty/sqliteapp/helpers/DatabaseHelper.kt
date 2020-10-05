package com.elitizamaty.sqliteapp.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.elitizamaty.sqliteapp.BookModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        val DATABASE_NAME = "Books.db"
        val TABLE_BOOK_NAME = "BooksTable"
        val COLUMN_BOOK_ID = "BookId"
        val COLUMN_BOOK_NAME = "BookName"
        val COLUMN_BOOK_PUBLICATION = "BookPublication"
        val COLUMN_BOOK_PRICE = "BookPrice"
        val COLUMN_BOOK_AUTHOR = "BookAuthor"
        val COLUMN_BOOK_PUBLICATION_DATE = "BookPublicationDate"
        val COLUMN_BOOK_CREATED_DATE = "BookCreatedDate"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BOOK_TABLE =
            "CREATE TABLE $TABLE_BOOK_NAME ($COLUMN_BOOK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_BOOK_NAME TEXT, $COLUMN_BOOK_PUBLICATION TEXT, $COLUMN_BOOK_PRICE TEXT, $COLUMN_BOOK_AUTHOR TEXT, $COLUMN_BOOK_PUBLICATION_DATE TEXT, $COLUMN_BOOK_CREATED_DATE TEXT )"
        db!!.execSQL(CREATE_BOOK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_BOOK_NAME")
        onCreate(db)
    }

    // BOOK TABLE OPERATIONS

    fun insertBook(bookModel: BookModel): Boolean {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COLUMN_BOOK_NAME, bookModel.bookName)
        contentValue.put(COLUMN_BOOK_PUBLICATION, bookModel.bookPublication)
        contentValue.put(COLUMN_BOOK_PRICE, bookModel.bookPrice)
        contentValue.put(COLUMN_BOOK_AUTHOR, bookModel.bookAuthor)
        contentValue.put(COLUMN_BOOK_PUBLICATION_DATE, bookModel.bookPublicationDate)
        contentValue.put(COLUMN_BOOK_CREATED_DATE, bookModel.bookCreatedDate)
        val insertId = database.insert(TABLE_BOOK_NAME, null, contentValue)
        if (insertId < 1) {
            return false
        } else {
            return true
        }
    }
}