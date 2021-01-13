package com.example.helloworld

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val listIntent = Intent(applicationContext, ListActivity::class.java)
        bt_mojalista.setOnClickListener {
            startActivity(listIntent)
        }

        val sharedListIntent = Intent(applicationContext, SharedListActivity::class.java)
        bt_dzielonalista.setOnClickListener {
            startActivity(sharedListIntent)
        }

        val shopListIntent = Intent(applicationContext, ShopListActivity::class.java)
        bt_listasklepow.setOnClickListener {
            startActivity(shopListIntent)
        }

        val shopMapIntent = Intent(applicationContext, MapsActivity::class.java)
        bt_mapasklepow.setOnClickListener {
            startActivity(shopMapIntent)
        }

        bt_wyloguj.setOnClickListener {
            auth.signOut()
            val intent4 = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent4)
            Toast.makeText(this, "Wylogowanie powiodło się.", Toast.LENGTH_SHORT).show()
        }

        val optionsIntent = Intent(applicationContext, OptionsActivity::class.java)
        bt_ustawienia.setOnClickListener {
            startActivity(optionsIntent)
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel()
    {
        val notificationChannel = NotificationChannel(
            "shopChannel",
            "Shop channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = "channel for shops"

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}