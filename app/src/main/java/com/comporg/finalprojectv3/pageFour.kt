package com.comporg.finalprojectv3

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PageFour : AppCompatActivity() {

    // define the global variable
    private lateinit var question3: TextView
    // Add button Move previous activity
    private lateinit var previous_button: Button
    // Add Button for Instagram
    private lateinit var Insta_button : Button
    // Twitter
    private lateinit var Twit_button : Button
    //Whatsapp
    private lateinit var WA_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagefour)

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the Button and textview.
        previous_button = findViewById(R.id.third_activity_previous_button)
        question3 = findViewById(R.id.question3_id)
        Insta_button = findViewById(R.id.insta_button)
        Twit_button = findViewById(R.id.twitter_button)
        WA_button = findViewById(R.id.facebook_button)

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message
        question3.text = "Temporary Community Page. Just Imagine some fancy links  here".trimIndent()

        // Add_button add clicklistener
        previous_button.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called SecondActivity with the following code:
            val intent = Intent(this, PageTwo::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }

        Insta_button.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*" // Set the type of content to share, e.g., image
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///path/to/your/image.jpg")) // Replace with the URI of your image file
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool post!") // Optional: Add a text to share
            shareIntent.setPackage("com.instagram.android") // Specify Instagram package

            try {
                startActivity(shareIntent)
            } catch (e: ActivityNotFoundException) {
                // Instagram app not installed, handle error
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
                startActivity(browserIntent)
            }
        }

        Twit_button.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*" // Set the type of content to share, e.g., image
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///path/to/your/image.jpg")) // Replace with the URI of your image file
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool post!") // Optional: Add a text to share
            shareIntent.setPackage("com.twitter.android") // Specify Instagram package

            try {
                startActivity(shareIntent)
            } catch (e: ActivityNotFoundException) {
                // Instagram app not installed, handle error
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
                startActivity(browserIntent)
            }
        }

        WA_button.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*" // Set the type of content to share, e.g., image
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///path/to/your/image.jpg")) // Replace with the URI of your image file
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool post!") // Optional: Add a text to share
            shareIntent.setPackage("com.whatsapp") // Specify Instagram package

            try {
                startActivity(shareIntent)
            } catch (e: ActivityNotFoundException) {
                // Instagram app not installed, handle error
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
                startActivity(browserIntent)
            }
        }


    }
}