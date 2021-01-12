package com.example.helloworld

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var location: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        shopViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ShopViewModel::class.java)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        permissionCheck()
        LocationServices.getFusedLocationProviderClient(this).lastLocation
                .addOnSuccessListener {
                    location = try {
                        Log.e(
                                "location-fused",
                                "Last location: ${it.latitude}, ${it.longitude}"
                        )
                        LatLng(it.latitude, it.longitude)
                    } catch (e: IllegalStateException) {
                        Log.e("location",
                                "warsaw"
                        )
                        LatLng(52.2, 21.0)
                    }
                }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        try {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        }
        catch(e: UninitializedPropertyAccessException)
        {
            Log.e("location",
                    "warsaw"
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(52.2, 21.0)))
        }

        for (shop in shopViewModel.allShops.filter { it.favorite }) {
            val coords = shop.coords.split(',')
            mMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        coords[0].toDouble(),
                        coords[1].toDouble()
                    )
                ).title(shop.name)
            )
        }
    }

    private fun permissionCheck() {
        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(perms, 0)
        }
    }

    fun turnOnGPS() {
        val service = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!service.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }
}