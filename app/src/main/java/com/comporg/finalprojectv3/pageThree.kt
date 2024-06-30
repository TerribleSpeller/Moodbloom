package com.comporg.finalprojectv3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener




class PageThree : AppCompatActivity(), OnItemClickListener<plantItem>   {
    private lateinit var databaseRef1: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference

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
        val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
        databaseRef1 = database.getReference("ListPlants")
        databaseRef2 = database.getReference("UserPlants/TestUser/UserPlants")

        databaseRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val plantMap = dataSnapshot.children.associate {
                    it.key to it.getValue(plantItem::class.java)
                }
                    plantMap.forEach { (key, value) ->
                        Log.d("Firebase", "Plant Name: ${value?.Name}, img = ${value?.Img} MoistMax: ${value?.MoistMax}, MoistMin: ${value?.MoistMin}")
                        data.add(plantItem(Img = value!!.Img, MoistMax = value!!.MoistMax, MoistMin = value!!.MoistMin, " ${value?.Name}" ))
                    }
                }
            override fun onCancelled(error: DatabaseError) {
                // Log the error
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })
        // This will pass the ArrayList to the  Adapter
        val adapter = CustomAdapter(data, this)
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
            val intent = Intent(this, PageOneActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(plantItem: plantItem) {
        Log.d("ItemClick", "Clicked on: ${plantItem.Name}")
        val thingToPush = userPlantItem(name = plantItem.Name, img = plantItem.Img)
        addPlantToFirebase(thingToPush)
    }

    override fun onCancelClick(plantItem: plantItem) {
        Log.d("ItemClick", "Clicked on: ${plantItem.Name}")
        //Just here to suppress an error.
    }

    private fun addPlantToFirebase(plantItem: userPlantItem) {
        val newPlantRef = databaseRef2.push()
        val newPlantId = newPlantRef.key
        val plantWithId = plantItem.copy(id = newPlantId!!)
        newPlantRef.setValue(plantWithId)
            .addOnSuccessListener {
                Log.d("Firebase", "Data added successfully with ID: $newPlantId")
                startActivity(Intent(this, PageOneActivity::class.java))
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error adding data", exception)
            }
    }
}


