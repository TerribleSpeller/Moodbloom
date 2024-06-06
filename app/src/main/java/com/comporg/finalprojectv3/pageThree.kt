package com.comporg.finalprojectv3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener




class PageThree : AppCompatActivity() {

    private lateinit var myRef: DatabaseReference
    private lateinit var textView: TextView
    // define the global variable
    private lateinit var question3: TextView
    // Add button Move previous activity
    private lateinit var previous_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagethree)

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the Button and textview.
        previous_button = findViewById(R.id.third_activity_previous_button)
        question3 = findViewById(R.id.question3_id)
        textView = findViewById(R.id.TextView)


        val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
        myRef = database.getReference("humidity") // Adjust the path to your data

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message
        question3.text = "Add Plants".trimIndent()
        //TODO: Make a self populating list
        // Add_button add clicklistener
        previous_button.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called SecondActivity with the following code:
            val intent = Intent(this, PageTwo::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the data from the snapshot
                val value = when (val data = dataSnapshot.value) {
                    is String -> data
                    is Long -> data.toString()
                    else -> "Unknown type"
                }                // Display the data in the TextView
                textView.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })
    }
}