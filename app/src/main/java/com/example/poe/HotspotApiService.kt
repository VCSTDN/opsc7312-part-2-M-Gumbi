package com.example.poe

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HotspotApiService {
    @GET("hotspots") // Replace with the actual endpoint
    fun getHotspots(
        @Query("apiKey") apiKey: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<List<Hotspot>> // Adjust the response type as needed
}
