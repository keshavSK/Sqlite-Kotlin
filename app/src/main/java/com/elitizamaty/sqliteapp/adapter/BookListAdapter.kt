package com.elitizamaty.sqliteapp.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.elitizamaty.sqliteapp.BookModel
import com.elitizamaty.sqliteapp.R
import com.elitizamaty.sqliteapp.helpers.DatabaseHelper
import com.elitizamaty.sqliteapp.screens.BookDetailsActivity
import com.elitizamaty.sqliteapp.screens.ListViewActivity


class BookListAdapter(var context: Context, var list: ArrayList<BookModel>) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    var dbHelper: DatabaseHelper = DatabaseHelper(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvBookTitle = itemView.findViewById<TextView>(R.id.rowBookTitle)
        var tvBookPublication = itemView.findViewById<TextView>(R.id.rowBookPublication)
        var tvBookPrice = itemView.findViewById<TextView>(R.id.rowBookPrice)
        var imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)
        var layout = itemView.findViewById<LinearLayout>(R.id.rowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_book_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvBookTitle.text = list[position].bookName
        holder.tvBookPublication.text = list[position].bookPublication
        holder.tvBookPrice.text = "\u20B9${list[position].bookPrice}"
        holder.imgDelete.setOnClickListener {
            var value = dbHelper.deleteRecord(list[position].bookId!!)
            if (value == true) {
                Toast.makeText(context, "Record deleted", Toast.LENGTH_LONG).show()
                var intent = Intent(context, ListViewActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            } else {
                Toast.makeText(context, "Please try again. record not deleted", Toast.LENGTH_LONG).show()
            }
        }
        holder.layout.setOnClickListener {
            var intent = Intent(context, BookDetailsActivity::class.java)
            intent.putExtra("BookId", list[position].bookId)
            context.startActivity(intent)
        }
    }
}