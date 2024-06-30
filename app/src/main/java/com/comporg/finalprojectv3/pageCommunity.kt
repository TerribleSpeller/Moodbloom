package com.comporg.finalprojectv3

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PageFour : AppCompatActivity() {
    private lateinit var question3: TextView
    private lateinit var previous_button: Button
    private lateinit var Insta_button : Button
    // Twitter
    private lateinit var Twit_button : Button
    //Whatsapp
    private lateinit var WA_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagefour)

        previous_button = findViewById(R.id.third_activity_previous_button)
        question3 = findViewById(R.id.question3_id)
        Insta_button = findViewById(R.id.insta_button)
        Twit_button = findViewById(R.id.twitter_button)
        WA_button = findViewById(R.id.facebook_button)

        question3.text = "Temporary Community Page. Just Imagine some fancy links  here".trimIndent()

        previous_button.setOnClickListener {

            val intent = Intent(this, PageTwo::class.java)
            startActivity(intent)
        }

        Insta_button.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*" 
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///path/to/your/image.jpg")) 
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool post!") 
            try {
                startActivity(shareIntent)
            } catch (e: ActivityNotFoundException) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
                startActivity(browserIntent)
            }
        }

        Twit_button.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///path/to/your/image.jpg")) 
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool post!")
            shareIntent.setPackage("com.twitter.android") 
            try {
                startActivity(shareIntent)
            } catch (e: ActivityNotFoundException) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
                startActivity(browserIntent)
            }
        }

        WA_button.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*" 
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///path/to/your/image.jpg")) 
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool post!") 
            shareIntent.setPackage("com.whatsapp") 
            try {
                startActivity(shareIntent)
            } catch (e: ActivityNotFoundException) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
                startActivity(browserIntent)
            }
        }


    }
}