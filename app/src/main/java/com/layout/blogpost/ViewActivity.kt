package com.layout.blogpost

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

            val listView = findViewById<ListView>(R.id.list_view)

        val sharedPreferences = getSharedPreferences("BLOG_POSTS", Context.MODE_PRIVATE)
        val postsMap = sharedPreferences.all

        val titles = mutableListOf<String>()
        val postDates = mutableListOf<String>()
        val postBy = mutableListOf<String>()

        for (entry in postsMap) {
            val post = entry.value.toString().split("\n\n")
            titles.add(entry.key)
            postDates.add(post[1].substring(11))
            postBy.add(post[1].substring(11))
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titles)
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val intent = Intent(applicationContext, PostDetailsActivity::class.java)
                intent.putExtra("title", titles[position])
                startActivity(intent)
            }
    }
}