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
        database.close()
        if (insertId < 1) {
            return false
        } else {
            return true
        }
    }

    fun getBookList(): ArrayList<BookModel> {
        var bookList = ArrayList<BookModel>()
        var database = this.readableDatabase
        var sql = "SELECT * FROM $TABLE_BOOK_NAME"
        var cursor = database.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val bookModel = BookModel()
                bookModel.bookId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_ID))
                bookModel.bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME))
                bookModel.bookPublication = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PUBLICATION))
                bookModel.bookPrice = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PRICE))
                bookList.add(bookModel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return bookList
    }

    fun deleteRecord(bookId: Int): Boolean {
        var database = this.writableDatabase
        var deletedRecord = database.delete(TABLE_BOOK_NAME, "$COLUMN_BOOK_ID = ?", arrayOf(bookId.toString()))
        database.close()
        if (deletedRecord != -1) {
            return true
        } else {
            return false
        }

    }

    fun getBookDetails(bookId: Int): BookModel {
        val bookModel = BookModel()
        var database = this.readableDatabase
        var sql = "SELECT * FROM $TABLE_BOOK_NAME WHERE $COLUMN_BOOK_ID = $bookId"
        var cursor = database.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            bookModel.bookId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_ID))
            bookModel.bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME))
            bookModel.bookPublication = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PUBLICATION))
            bookModel.bookPrice = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PRICE))
            bookModel.bookAuthor = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR))
            bookModel.bookPublicationDate = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PUBLICATION_DATE))
            bookModel.bookCreatedDate = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_CREATED_DATE))
        }
        cursor.close()
        database.close()
        return bookModel
    }
}