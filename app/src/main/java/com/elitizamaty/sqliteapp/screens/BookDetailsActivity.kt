package com.elitizamaty.sqliteapp.screens

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elitizamaty.sqliteapp.BookModel
import com.elitizamaty.sqliteapp.R
import com.elitizamaty.sqliteapp.helpers.DatabaseHelper
import kotlinx.android.synthetic.main.activity_book_details.*
import java.util.*

class BookDetailsActivity : AppCompatActivity() {
    var bookId: Int? = null
    var bookModel = BookModel()
    var bookPublishedDate: String? = null
    var dayOfMonth: Int? = null
    var month: Int? = null
    var year: Int? = null
    var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        var databaseHelper = DatabaseHelper(this)
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
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
        btnUpdateSubmitBook.setOnClickListener {
            if (edtUpdateBookName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edtUpdateBookPublication.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter book publication", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edtUpdateBookPrice.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter book price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edtUpdateBookAuthor.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter book author", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (tvUpdateBookPublishedDate.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select book published date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var bookModel = BookModel()
            bookModel.bookName = edtUpdateBookName.text.toString().trim()
            bookModel.bookPublication = edtUpdateBookPublication.text.toString().trim()
            bookModel.bookPrice = edtUpdateBookPrice.text.toString().trim()
            bookModel.bookAuthor = edtUpdateBookAuthor.text.toString().trim()
            bookModel.bookPublicationDate = tvUpdateBookPublishedDate.text.toString().trim()
            bookModel.bookId = bookId
            var returnValue: Boolean = databaseHelper.updateBook(bookModel)
            if (returnValue) {
                Toast.makeText(this, "Record updated...", Toast.LENGTH_SHORT).show()
                var newIntent = Intent(this, ListViewActivity::class.java)
                startActivity(newIntent)
                finish()
            } else {
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
            }
        }
        tvUpdateBookPublishedDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker =
                DatePickerDialog(
                        this,
                        DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                            bookPublishedDate = "$dayOfMonth-${month + 1}-$year"
                            tvUpdateBookPublishedDate.text = bookPublishedDate
                        },
                        year!!,
                        month!!,
                        dayOfMonth!!
                )
        datePicker.show()
    }
}