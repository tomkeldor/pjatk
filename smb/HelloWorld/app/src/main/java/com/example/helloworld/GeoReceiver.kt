package com.example.helloworld

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.e(this::class.java.name, "Triggered!")

        val event = GeofencingEvent.fromIntent(intent)
        val triggeredGeofences = event.triggeringGeofences
        var notification : Notification
        for(geo in triggeredGeofences){
            if(event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                Log.e("enter", geo.requestId)
                notification = NotificationCompat.Builder(context, "shopChannel")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Witaj w sklepie ${intent.getStringExtra("shopName")}!")
                    .setContentText("Promocja dnia: ${intent.getStringExtra("shopDesc")}")
                    .setAutoCancel(true)
                    .build()
            }
            else
            {
                Log.e("leave", geo.requestId)
                notification = NotificationCompat.Builder(context, "shopChannel")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Opuściłeś sklep ${intent.getStringExtra("shopName")}!")
                    .setContentText("Zapraszamy ponownie!")
                    .setAutoCancel(true)
                    .build()
            }

            NotificationManagerCompat.from(context).notify(
                intent.getIntExtra("id", 0),
                notification)
        }
    }
}