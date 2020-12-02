package com.example.myapp

import android.app.ListActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class ProductReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.i(this::class.java.name, "Triggered!")
        val listIntent = Intent()
        listIntent.component = ComponentName(
                "com.example.helloworld",
                "com.example.helloworld.MainActivity")//todo:scrollToPosition - do edycji produktu
        val pendingIntent = PendingIntent.getActivity(
            context,
            intent.getIntExtra("id", 0),
            listIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val data : String = intent.getStringExtra("product")!!
        val product : Product = Json.decodeFromString<Product>(data)
        var channel = intent.getStringExtra("channel")

        if(channel == null)
            channel = "default"
        val notification = NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Product added")
            .setContentText(product.name)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(
            intent.getIntExtra("id", 0),
            notification)
    }
}