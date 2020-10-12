package com.elitizamaty.sqliteapp.screens

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elitizamaty.sqliteapp.BookModel
import com.elitizamaty.sqliteapp.R
import com.elitizamaty.sqliteapp.helpers.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var calendar = Calendar.getInstance()
    var bookPublishedDate: String? = null
    var dayOfMonth: Int? = null
    var month: Int? = null
    var year: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var databaseHelper = DatabaseHelper(this)
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        btnSubmitBook.setOnClickListener {
            var bookName = edtBookName.text.toString().trim()
            var bookPublication = edtBookPublication.text.toString().trim()
            var bookPrice = edtBookPrice.text.toString().trim()
            var bookAuthor = edtBookAuthor.text.toString().trim()
            var bookModel = BookModel()
            bookModel.bookName = bookName
            bookModel.bookPublication = bookPublication
            bookModel.bookPrice = bookPrice
            bookModel.bookAuthor = bookAuthor
            bookModel.bookPublicationDate = bookPublishedDate
            val simpleDateFormat = SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault())
            val currentDate = Calendar.getInstance().time
            val currentDateTime = simpleDateFormat.format(currentDate)
            bookModel.bookCreatedDate = currentDateTime
            var returnValue = databaseHelper.insertBook(bookModel)
            if (returnValue == true) {
                Toast.makeText(this, "Book inserted successfully", Toast.LENGTH_LONG).show()
                var intent = Intent(this, ListViewActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Book not inserted. Please try again", Toast.LENGTH_LONG)
                        .show()
            }
        }

        tvBookPublishedDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker =
                DatePickerDialog(
                        this,
                        DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                            bookPublishedDate = "$dayOfMonth-${month + 1}-$year"
                            tvBookPublishedDate.text = bookPublishedDate
                        },
                        year!!,
                        month!!,
                        dayOfMonth!!
                )
        datePicker.show()
    }
}