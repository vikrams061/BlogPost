package com.layout.blogpost

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PostDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        val titleTextView = findViewById<TextView>(R.id.title_text_view)
        val detailsTextView = findViewById<TextView>(R.id.details_text_view)

        val title = intent.getStringExtra("title")

        val sharedPreferences = getSharedPreferences("BLOG_POSTS", Context.MODE_PRIVATE)
        val post = sharedPreferences.getString(title, "")?.split("\n\n")

        titleTextView.text = title
        detailsTextView.text = post?.get(0)
    }
}