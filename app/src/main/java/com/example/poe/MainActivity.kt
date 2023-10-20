package com.example.poe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val openLocation = findViewById<View>(R.id.buttonLocation1)
        openLocation.setOnClickListener {
            // Create an Intent to open the Map activity
            val intent = Intent(this@MainActivity, Map::class.java)
            startActivity(intent)
        }

        val openCreateSighting = findViewById<View>(R.id.buttonCreateSightings1)
        openCreateSighting.setOnClickListener {
            // Create an Intent to open the regSighting activity
            val intent = Intent(this@MainActivity, regSighting::class.java)
            startActivity(intent)
        }

        val openViewSighting = findViewById<View>(R.id.buttonViewSightings1)
        openViewSighting.setOnClickListener {
            // Create an Intent to open the DisplaySighting activity
            val intent = Intent(this@MainActivity, DisplaySighting::class.java)
            startActivity(intent)
        }

        val openRare = findViewById<View>(R.id.buttonRarest1)
        openRare.setOnClickListener {
            // Create an Intent to open the Checklist activity
            val intent = Intent(this@MainActivity, Checklist::class.java)
            startActivity(intent)
        }

        val openSettings = findViewById<View>(R.id.buttonSettings1)
        openSettings.setOnClickListener {
            // Create an Intent to open the Settings activity
            val intent = Intent(this@MainActivity, Settings::class.java)
            startActivity(intent)
        }

        val logout = findViewById<View>(R.id.buttonLogout1)
        logout.setOnClickListener {
            // Create an Intent to open the LoginScreen activity
            val intent = Intent(this@MainActivity, LoginScreen::class.java)
            startActivity(intent)
        }
    }
}
