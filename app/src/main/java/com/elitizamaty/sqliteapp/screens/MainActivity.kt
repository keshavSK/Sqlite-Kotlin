package com.elitizamaty.sqliteapp.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elitizamaty.sqliteapp.BookModel
import com.elitizamaty.sqliteapp.R
import com.elitizamaty.sqliteapp.helpers.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var databaseHelper = DatabaseHelper(this)
        btnSubmitBook.setOnClickListener {
            var bookName = edtBookName.text.toString().trim()
            var bookPublication = edtBookPublication.text.toString().trim()
            var bookPrice = edtBookPrice.text.toString().trim()
            var bookAuthor = edtBookAuthor.text.toString().trim()
            // selected from date picker
            var bookPublicationDate = "12-12-2010"
            // current date and time
            var createdDate = "05-10-2020 09:31:00"
            var bookModel = BookModel()
            bookModel.bookName = bookName
            bookModel.bookPublication = bookPublication
            bookModel.bookPrice = bookPrice
            bookModel.bookAuthor = bookAuthor
            bookModel.bookPublicationDate = bookPublicationDate
            bookModel.bookCreatedDate = createdDate
            var returnValue = databaseHelper.insertBook(bookModel)
            if (returnValue == true) {
                Toast.makeText(this, "Book inserted successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Book not inserted. Please try again", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}