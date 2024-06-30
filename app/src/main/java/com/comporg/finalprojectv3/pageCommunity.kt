package com.comporg.finalprojectv3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PageCommunity : AppCompatActivity() {

    private lateinit var question3: TextView
    private lateinit var previousButton: ImageView
    private lateinit var instaButton: Button
    private lateinit var twitterButton: Button
    private lateinit var facebookButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagefour)

        // Initialize UI elements
        question3 = findViewById(R.id.pagefourtext)
        previousButton = findViewById(R.id.third_activity_previous_button)
        instaButton = findViewById(R.id.insta_button)
        twitterButton = findViewById(R.id.twitter_button)
        facebookButton = findViewById(R.id.facebook_button)

        // Set text for question3 TextView
        question3.text = "Follow us for more Exciting Updates"
        previousButton.setOnClickListener {
            val intent = Intent(this, PageOneActivity::class.java)
            startActivity(intent)
        }

        instaButton.setOnClickListener {
            openExternalLink("https://www.instagram.com")
        }

        twitterButton.setOnClickListener {
            openExternalLink("https://twitter.com")
        }

        facebookButton.setOnClickListener {
            openExternalLink("https://www.facebook.com")
        }
    }

    // Function to open external links
    private fun openExternalLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
