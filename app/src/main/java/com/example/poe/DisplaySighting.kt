package com.example.poe

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplaySighting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.displaysighting)

        val layout = findViewById<LinearLayout>(R.id.birdEntriesLayout)

        val species = intent.getStringExtra("species")
        val location = intent.getStringExtra("location")
        val date = intent.getStringExtra("date")
        val description = intent.getStringExtra("description")
        val imageUriString = intent.getStringExtra("imageUri")

        if (species != null && location != null && date != null && description != null) {
            val sightingView = layoutInflater.inflate(R.layout.birdchecklist, null)

            val speciesTextView = sightingView.findViewById<TextView>(R.id.speciesTextView)
            val locationTextView = sightingView.findViewById<TextView>(R.id.locationTextView)
            val dateTextView = sightingView.findViewById<TextView>(R.id.dateTextView)

            speciesTextView.text = "Species: $species"
            locationTextView.text = "Location: $location"
            dateTextView.text = "Date: $date"

            layout.addView(sightingView)
        }

        val backButton = findViewById<View>(R.id.imageButton2)
        backButton.setOnClickListener {
            finish() // Close this activity and return to the previous one
        }
    }
}
