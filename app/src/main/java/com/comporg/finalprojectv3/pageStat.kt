package com.comporg.finalprojectv3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*

class PageStat : AppCompatActivity() {

    private lateinit var databaseRef1: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
    private lateinit var humidView: TextView
    private lateinit var moistView: TextView
    private lateinit var plantNameView: TextView
    private lateinit var additionalImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statpage)

        // Initialize views
        val bottomSheet: NestedScrollView = findViewById(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height)
        bottomSheetBehavior.isFitToContents = false

        // Customize max height for expanded state
        bottomSheetBehavior.setExpandedOffset(resources.getDimensionPixelSize(R.dimen.bottom_sheet_expanded_offset))

        humidView = findViewById(R.id.humidView)
        moistView = findViewById(R.id.moistView)
        plantNameView = findViewById(R.id.plantNameView)
        additionalImageView = findViewById(R.id.additional_ImageView)

        // Set up Firebase database references
        val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
        databaseRef1 = database.getReference("CurrentData/humidity")
        databaseRef2 = database.getReference("CurrentData/moisture")

        // Retrieve humidity data from Firebase
        databaseRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val humidity = dataSnapshot.value
                humidView.text = humidity.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase", "Failed to read humidity value.", error.toException())
            }
        })

        // Retrieve moisture data from Firebase
        databaseRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val moisture = dataSnapshot.value
                moistView.text = moisture.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase", "Failed to read moisture value.", error.toException())
            }
        })

        // Set up BottomSheetBehavior callback to detect state changes
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    showAdditionalInfo(true)
                } else {
                    showAdditionalInfo(false)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Unused, but required to implement
            }
        })

        // Initialize the bottom sheet state
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        showAdditionalInfo(false)
    }

    private fun showAdditionalInfo(show: Boolean) {
        additionalImageView.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
            // Set visibility for additional info views as needed
        }
    }
}

