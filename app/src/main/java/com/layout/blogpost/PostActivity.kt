package com.layout.blogpost

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        val submitButton = findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener {
            val titleEditText = findViewById<EditText>(R.id.title_edit_text)
            val descriptionEditText = findViewById<EditText>(R.id.description_edit_text)

            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            val sharedPreferences = getSharedPreferences("BLOG_POSTS", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val postDate = Date().toString()

            editor.putString(title, "$description\n\nPosted on: $postDate\nPosted by: You")
            editor.apply()

            Toast.makeText(this, "Blog posted successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}