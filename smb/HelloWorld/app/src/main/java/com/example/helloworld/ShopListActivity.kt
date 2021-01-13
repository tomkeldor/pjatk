package com.example.helloworld

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.databinding.ActivityShopListBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng

class ShopListActivity : AppCompatActivity() {

    private lateinit var location: LatLng
    private lateinit var geoClient: GeofencingClient
    private var id = 0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv1.layoutManager = LinearLayoutManager(baseContext)
        binding.rv1.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                DividerItemDecoration.VERTICAL
            )
        )
        geoClient = LocationServices.getGeofencingClient(this)
        //geoClient.removeGeofences()
        val shopViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ShopViewModel::class.java)
        binding.rv1.adapter = ShopAdapter(shopViewModel)
        (binding.rv1.adapter as ShopAdapter).setShops()

        binding.bt1.setOnClickListener {
            LocationServices.getFusedLocationProviderClient(this).getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,null)
                .addOnSuccessListener {
                    location = try {
                        Log.e(
                            "location-fused",
                            "Last location: ${it.latitude}, ${it.longitude}"
                        )
                        LatLng(it.latitude, it.longitude)
                    } catch (e: IllegalStateException) {
                        Log.e(
                            "location",
                            "warsaw"
                        )
                        LatLng(52.2, 21.0)
                    }

                    val shop = Shop(
                        binding.et1.text.toString(),
                        binding.et2.text.toString(),
                        String.format("%f,%f", location.latitude, location.longitude),
                        binding.et3.text.toString()
                    )
                    (binding.rv1.adapter as ShopAdapter).addShop(shop)
                    addGeo(shop)
                }
        }
    }

    @SuppressLint("MissingPermission")
    fun addGeo(shop: Shop)
    {
        val coords = shop.coords.split(',')
        val radius = shop.radius.toFloat()
        Log.e("radius", radius.toString())
        val geofence = Geofence.Builder()
            .setRequestId("Geofence${(0..1000000).random()}")
            .setCircularRegion(
                coords[0].toDouble(),
                coords[1].toDouble(),
                radius
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val geoRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val intent = Intent(this, GeoReceiver::class.java)
        intent.putExtra("shopName", shop.name)
        intent.putExtra("shopDesc", shop.desc)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        geoClient.addGeofences(geoRequest, pendingIntent)
            .addOnSuccessListener {
                Log.e("Dodano geofence", geofence.requestId)
            }
            .addOnFailureListener {
                Log.e("Niedodano geofence", "error")
            }
    }
}