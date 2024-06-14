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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PageUserPlants: AppCompatActivity() {
    private lateinit var databaseRef1: DatabaseReference //To Call the database to see plants that one has.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagethree)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<plantItem>()

        // This loop will create 20 Views containing
        // the image with the count of view
//        for (i in 1..20) {
//            data.add(plantItem(R.drawable.button, "Item " + i))
//        }



        val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
        databaseRef1 = database.getReference("UserPlants/TestUser/UserPlants") // Adjust the path to your data

        databaseRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the data from the snapshot
                val plantMap = dataSnapshot.children.associate {
                    it.key to it.getValue(userPlantItem::class.java)
                }

                // Display the data in the TextView or process it as needed
                plantMap.forEach { (key, value) ->
                    Log.d("Firebase", "Plant Name: ${value?.Name}, ID = ${value?.ID} Humidity: ${value?.Humidity}, Moisture: ${value?.Moisture}, Temperature: ${value?.Temperature}")
                    // Append data to the TextView for demonstration
                    //textView.append("Plant Name: ${value?.Name}, MoistMax: ${value?.MoistMax}, MoistMin: ${value?.MoistMin}\n")
                    // data.add(plantItem(MoistMax = value!!.MoistMax, MoistMin = value!!.MoistMin, " ${value?.Name}" ))

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error
                Log.w("Firebase", "Failed to read value.", error.toException())
            }


        })

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)
        Log.d("Firebase", data.toString())

        databaseRef1.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                recyclerview.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

}}