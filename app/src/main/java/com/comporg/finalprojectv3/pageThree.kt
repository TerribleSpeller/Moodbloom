package com.comporg.finalprojectv3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener




class PageThree : AppCompatActivity() {
    private lateinit var databaseRef1: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagethree)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        val layoutManager = GridLayoutManager(this, 2)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = layoutManager
        val previous_button = findViewById<ImageView>(R.id.backButton)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<plantItem>()

        // This loop will create 20 Views containing
        // the image with the count of view
//        for (i in 1..20) {
//            data.add(plantItem(R.drawable.button, "Item " + i))
//        }



        val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
        databaseRef1 = database.getReference("ListPlants") // Adjust the path to your data

        databaseRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the data from the snapshot
                val plantMap = dataSnapshot.children.associate {
                    it.key to it.getValue(plantItem::class.java)
                }

                // Display the data in the TextView or process it as needed
                    plantMap.forEach { (key, value) ->
                        Log.d("Firebase", "Plant Name: ${value?.Name}, img = ${value?.Img} MoistMax: ${value?.MoistMax}, MoistMin: ${value?.MoistMin}")
                        // Append data to the TextView for demonstration
                        //textView.append("Plant Name: ${value?.Name}, MoistMax: ${value?.MoistMax}, MoistMin: ${value?.MoistMin}\n")
                        data.add(plantItem(Img = value!!.Img, MoistMax = value!!.MoistMax, MoistMin = value!!.MoistMin, " ${value?.Name}" ))

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

        previous_button.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called oneActivity with the following code
            val intent = Intent(this, PageOneActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }


        
        // Setting the Adapter with the recyclerview
    }
}

