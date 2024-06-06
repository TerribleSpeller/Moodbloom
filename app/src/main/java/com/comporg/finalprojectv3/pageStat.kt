package com.comporg.finalprojectv3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PageStat: AppCompatActivity() {

    private lateinit var databaseRef1: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference

    private lateinit var humidView: TextView
    private lateinit var moistView: TextView
    private lateinit var humidViewText: TextView
    private lateinit var moistViewText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statpage)

        humidView = findViewById(R.id.humidViewXML)
        moistView = findViewById(R.id.moistViewXML)

        humidViewText = findViewById(R.id.humidViewText)
        moistViewText = findViewById(R.id.moistViewText)

        humidViewText.text = "Humidity :"
        moistViewText.text = "Moisture :"


        val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
        databaseRef1 = database.getReference("humidity") // Adjust the path to your data
        databaseRef2 = database.getReference("moisture") // Adjust the path to your data

        // Find the ComposeView in the layout
        val composeView: ComposeView = findViewById(R.id.compose_view)

        databaseRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the data from the snapshot
                val value = when (val data = dataSnapshot.value) {
                    is String -> data
                    is Long -> data.toString()
                    else -> "Unknown type"
                }                // Display the data in the TextView
                humidView.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })

        databaseRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the data from the snapshot
                val value = when (val data = dataSnapshot.value) {
                    is String -> data
                    is Long -> data.toString()
                    else -> "Unknown type"
                }                // Display the data in the TextView
                moistView.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })

        // Set the content of the ComposeView
        composeView.setContent {
            // Call your composable function here
            testText()
        }




    }
}

@Composable
fun testText(

) {
    Text(text = "Amongus")
}




