package com.example.helloworld

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ShopRepository(private val dbRef: DatabaseReference) {

    suspend fun insert(shop: Shop) {
        val key = dbRef.push().key
        if (key == null) {
            Log.w("error", "Couldn't get push key for shops")
            return
        }

        shop.id = key
        val shopValues = shop.toMap()

        val childUpdates = hashMapOf<String, Any>(
                key to shopValues
        )

        dbRef.updateChildren(childUpdates)
    }

    suspend fun update(shop: Shop){
        val shopValues = shop.toMap()

        val childUpdates = hashMapOf<String, Any>(
                shop.id to shopValues
        )

        dbRef.updateChildren(childUpdates)
    }

    suspend fun delete(key: String){
        dbRef.child(key).removeValue()
    }
}