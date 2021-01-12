package com.example.helloworld

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.databinding.ActivityShopListBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.*

class ShopListActivity : AppCompatActivity() {

    private lateinit var location: LatLng

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

        val shopViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ShopViewModel::class.java)
        binding.rv1.adapter = ShopAdapter(shopViewModel)
        (binding.rv1.adapter as ShopAdapter).setShops()
        binding.bt1.setOnClickListener {
            (binding.rv1.adapter as ShopAdapter).addShop(
                    Shop(
                            binding.et1.text.toString(),
                            binding.et2.text.toString(),
                            String.format("%f,%f",location.latitude,location.longitude),
                            binding.et3.text.toString().toLong()
                    )
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
}