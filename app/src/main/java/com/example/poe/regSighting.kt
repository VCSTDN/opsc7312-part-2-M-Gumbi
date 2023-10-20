package com.example.poe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class regSighting : AppCompatActivity() {
    private lateinit var uploadButton: Button
    private lateinit var selectedImageUri: Uri
    private val sightingList = ArrayList<Sighting>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registersighting)

        uploadButton = findViewById(R.id.uploadImageButton)

        // Handle image selection when the "Attach Image" button is clicked
        uploadButton.setOnClickListener {
            openImagePicker()
        }

        val backHome = findViewById<View>(R.id.imageButton1)
        backHome.setOnClickListener {
            val intent = Intent(this@regSighting, MainActivity::class.java)
            startActivity(intent)
        }

        // Handle "Log Sighting" button click
        val logSightingButton = findViewById<Button>(R.id.loginButton)
        logSightingButton.setOnClickListener {
            val speciesEditText = findViewById<EditText>(R.id.speciesedittext)
            val locationSearchView = findViewById<SearchView>(R.id.idSearchView) // Use androidx SearchView
            val datePicker = findViewById<DatePicker>(R.id.smallDatePicker)
            val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)

            val species = speciesEditText.text.toString()
            val location = locationSearchView.query.toString()
            val date = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"
            val description = descriptionEditText.text.toString()

            if (selectedImageUri != null) {
                storeSightingData(species, location, date, description, selectedImageUri)
            } else {
                storeSightingData(species, location, date, description, Uri.EMPTY)
            }
        }
    }

    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            uploadButton.text = "Image Attached"
        }
    }

    private fun openImagePicker() {
        imagePicker.launch("image/*")
    }

    private fun storeSightingData(species: String, location: String, date: String, description: String, imageUri: Uri) {
        val newSighting = Sighting(species, location, date, description, imageUri)
        sightingList.add(newSighting)

        // Pass the data to the DisplaySighting activity
        val intent = Intent(this, DisplaySighting::class.java)
        intent.putExtra("species", species)
        intent.putExtra("location", location)
        intent.putExtra("date", date)
        intent.putExtra("description", description)
        intent.putExtra("imageUri", imageUri.toString())
        startActivity(intent)
    }
}



