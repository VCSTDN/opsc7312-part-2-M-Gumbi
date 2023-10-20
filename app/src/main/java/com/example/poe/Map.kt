package com.example.poe

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Random

class Map : FragmentActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_REQUEST_CODE = 1000
    private val apiKey = "5ir2e7v22d2u" // This is eBird API key
    private var selectedDistance: Double = 5000.0 // Default distance in meters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mapscreen)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val refreshButton = findViewById<Button>(R.id.refreshButton)
        refreshButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@setOnClickListener
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    clearMarkers()
                    addCustomMarkers(currentLatLng, selectedDistance)
                }
            }
        }

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this@Map, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        checkLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                ZoomOnMap(currentLatLng)
                addCustomMarkers(currentLatLng, selectedDistance)
            }
        }
    }

    private fun ZoomOnMap(latLng: LatLng) {
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(12.0f)
            .build()
        myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun addMarker(latLng: LatLng) {
        myMap.addMarker(MarkerOptions().position(latLng).title("Custom Marker"))
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        } else {
            myMap.isMyLocationEnabled = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                myMap.isMyLocationEnabled = true
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearMarkers() {
        myMap.clear()
    }

    private fun addCustomMarkers(currentLocation: LatLng, selectedDistance: Double) {
        val random = Random()
        val metersPerOffset = selectedDistance

        for (i in 1..20) {
            val offsetLat = (random.nextDouble() - 0.5) * 2 * 0.000008991 * metersPerOffset
            val offsetLng = (random.nextDouble() - 0.5) * 2 * 0.000011792 * metersPerOffset
            val customLatLng = LatLng(currentLocation.latitude + offsetLat, currentLocation.longitude + offsetLng)
            val customMarker = myMap.addMarker(MarkerOptions().position(customLatLng).title("Location $i"))

            customMarker?.tag = customLatLng

            myMap.setOnMarkerClickListener { marker ->
                val markerLocation = marker.tag as? LatLng
                if (markerLocation != null) {
                    requestDirectionsToMarker(currentLocation, markerLocation)
                }
                true
            }
        }
    }

    private fun requestDirectionsToMarker(currentLocation: LatLng, markerLatLng: LatLng) {
        val origin = "${currentLocation.latitude},${currentLocation.longitude}"
        val destination = "${markerLatLng.latitude},${markerLatLng.longitude}"

        val uri = "https://www.google.com/maps/dir/?api=1&origin=$origin&destination=$destination&travelmode=driving"
        val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showToast("Google Maps app is not installed.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
