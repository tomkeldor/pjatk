package com.example.helloworld

import android.util.Log
import com.google.firebase.database.DatabaseReference

class ProductRepository(private val dbRef: DatabaseReference) {

    suspend fun insert(product: Product) {
        val key = dbRef.push().key
        if (key == null) {
            Log.w("error", "Couldn't get push key for posts")
            return
        }

        product.id = key
        val productValues = product.toMap()

        val childUpdates = hashMapOf<String, Any>(
            key to productValues
        )

        dbRef.updateChildren(childUpdates)
    }
    
    suspend fun update(product: Product){
        val productValues = product.toMap()

        val childUpdates = hashMapOf<String, Any>(
            product.id to productValues
        )

        dbRef.updateChildren(childUpdates)
    }

    suspend fun delete(key: String){
        dbRef.child(key).removeValue()
    }
}