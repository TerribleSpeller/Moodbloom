package com.comporg.finalprojectv3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class PageUserPlants : AppCompatActivity(), OnItemClickListener<userPlantItem> {
    private lateinit var databaseRef1: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pageuserplants)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        val layoutManager = GridLayoutManager(this, 2) //Thanks.
        recyclerview.layoutManager = layoutManager

        val data = ArrayList<userPlantItem>() //I hate data.
        val database =
            FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
        databaseRef1 = database.getReference("UserPlants/TestUser/UserPlants")

        databaseRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(data) {
                    it.getValue(userPlantItem::class.java)
                }
                recyclerview.adapter?.notifyDataSetChanged() //I don't understand this.
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })

        val adapter = CustomAdapter2(data, this, this)
        recyclerview.adapter = adapter

        val previous_button = findViewById<ImageView>(R.id.backButton)

        previous_button.setOnClickListener {
            startActivity(Intent(this, PageOneActivity::class.java))
        }


    }
    override fun onItemClick(item: userPlantItem) {
        Log.d("ItemClick", "Clicked on: ${item.name}")
        // Handle item click for userPlantItem
    }

    override fun onCancelClick(item: userPlantItem) {
        Log.d("CancelClick", "Cancel clicked: ${item.id}")
        removePlantToFirebase(item)
    }

    private fun removePlantToFirebase(plantItem: userPlantItem) {
        // Generate loc
        val plantRef = databaseRef1.child(plantItem.id)
        // Removing the value at the specified reference
        Log.d("Firebase",plantRef.toString())
        plantRef.removeValue()
            .addOnSuccessListener {
                Log.d("Firebase", "Data removed successfully for ID: ${plantItem.id}")
                startActivity(Intent(this, PageOneActivity::class.java))
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error removing data", exception)
            }


    }
}
