package com.example.poe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {

    private lateinit var distanceUnitsRadioGroup: RadioGroup
    private lateinit var kilometersRadioButton: RadioButton
    private lateinit var milesRadioButton: RadioButton
    private lateinit var distanceTravelSlider: SeekBar
    private lateinit var sliderLeftLabel: TextView
    private lateinit var sliderRightLabel: TextView
    private lateinit var saveButton: Button
    private lateinit var logoutButton: Button
    private var selectedDistance: Double = 5000.0 // Default distance in meters (5 kilometers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        val imageButton3 = findViewById<ImageButton>(R.id.imageButton3)

        imageButton3.setOnClickListener {
            // Create an Intent to go back to the MainActivity
            val intent = Intent(this@Settings, MainActivity::class.java)
            startActivity(intent)
        }

        distanceUnitsRadioGroup = findViewById(R.id.distanceUnitsRadioGroup)
        kilometersRadioButton = findViewById(R.id.kilometersRadioButton)
        milesRadioButton = findViewById(R.id.milesRadioButton)
        distanceTravelSlider = findViewById(R.id.distanceTravelSlider)
        sliderLeftLabel = findViewById(R.id.sliderLeftLabel)
        sliderRightLabel = findViewById(R.id.sliderRightLabel)
        saveButton = findViewById(R.id.saveButton)
        logoutButton = findViewById(R.id.logoutButton)

        // Set the initial SeekBar progress based on the selected distance
        distanceTravelSlider.progress = (selectedDistance / 10).toInt()

        // Check the selected distance unit
        distanceUnitsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.kilometersRadioButton -> {
                    // Switch to kilometers
                    selectedDistance = (distanceTravelSlider.progress * 10).toDouble()
                    updateSliderLabels()
                }
                R.id.milesRadioButton -> {
                    // Switch to miles
                    selectedDistance = (distanceTravelSlider.progress * 10 / 1.60934).toDouble()
                    updateSliderLabels()
                }
            }
        }

        // Handle distance slider changes
        distanceTravelSlider.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedDistance = (progress * 10).toDouble()
                updateSliderLabels()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Handle when the user starts tracking the slider
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Handle when the user stops tracking the slider
            }
        })

        // Handle the Save button click
        saveButton.setOnClickListener {
            // Implement your logic to save settings
            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()
        }

        // Handle the Logout button click
        logoutButton.setOnClickListener {
            // Create an Intent to open the LoginScreen activity
            val intent = Intent(this@Settings, LoginScreen::class.java)
            startActivity(intent)
        }
    }

    private fun updateSliderLabels() {
        sliderLeftLabel.text = "$selectedDistance ${if (kilometersRadioButton.isChecked) "km" else "m"}"
        sliderRightLabel.text = "${selectedDistance * 1.60934} km"
    }
}
