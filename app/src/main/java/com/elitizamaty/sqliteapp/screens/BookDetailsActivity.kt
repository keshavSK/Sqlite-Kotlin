package com.elitizamaty.sqliteapp.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elitizamaty.sqliteapp.BookModel
import com.elitizamaty.sqliteapp.R
import com.elitizamaty.sqliteapp.helpers.DatabaseHelper
import kotlinx.android.synthetic.main.activity_book_details.*

class BookDetailsActivity : AppCompatActivity() {
    var bookId: Int? = null
    var bookModel = BookModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        var databaseHelper = DatabaseHelper(this)
        var intent = intent
        if (intent.extras != null) {
            bookId = intent.extras!!.getInt("BookId")
            bookModel = databaseHelper.getBookDetails(bookId!!)
            if (bookModel.bookId == null) {
                Toast.makeText(this, "Book details not available", Toast.LENGTH_LONG).show()
                return
            }
            edtUpdateBookName.setText(bookModel.bookName)
            edtUpdateBookPublication.setText(bookModel.bookPublication)
            edtUpdateBookPrice.setText(bookModel.bookPrice)
            edtUpdateBookAuthor.setText(bookModel.bookAuthor)
            tvUpdateBookPublishedDate.text = bookModel.bookPublicationDate

        } else {
            Toast.makeText(this, "Book details not available", Toast.LENGTH_LONG).show()
            return
        }
    }
}