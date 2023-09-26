package com.example.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.userapp.R
import com.example.userapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val customMarkerBitmap = BitmapFactory.decodeResource(resources, R.drawable.marker)
        val scaledBitmap = Bitmap.createScaledBitmap(customMarkerBitmap, 59, 59, false) // Set the desired size

        // Add a marker in Sydney and move the camera
        val lu = LatLng(26.8633, 80.9360)
        mMap.addMarker(MarkerOptions().position(lu).title("Lucknow University")
            .icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap)))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lu, 17f), 2000, null)

        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoContents(p0: Marker): View? {
                return null
            }

            override fun getInfoWindow(p0: Marker): View? {
                // Inflate the custom info window layout
                val view = layoutInflater.inflate(R.layout.custom_info_window_layout, null)
                // Customize the content of the custom info window here
                return view
            }

        })

    }
}