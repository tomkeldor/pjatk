package com.example.helloworld

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ShopViewModel(app: Application) : AndroidViewModel(app) {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repo: ShopRepository
    val allShops: MutableList<Shop>
    private val user: FirebaseUser?

    init {
        user = mAuth.currentUser
        allShops = arrayListOf()
        val database = FirebaseDatabase.getInstance()
        val reference : DatabaseReference = database.getReference("users/${user?.uid}/shops")
        var name: String
        var desc: String
        var coords: String
        var radius: Long
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (shop in snapshot.children) {
                    name = shop.child("name").value as String
                    desc = shop.child("desc").value as String
                    coords = shop.child("coords").value as String
                    radius = shop.child("radius").value as Long
                    val newShop = Shop(name, desc, coords, radius)
                    newShop.id = shop.child("id").value as String
                    if (!allShops.contains(newShop)) {
                        allShops.add(newShop)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadPost:onCancelled", error.toException())
            }
        })

        repo = ShopRepository(reference)
    }

    fun insert(shop: Shop) {
        CoroutineScope(IO).launch {
            repo.insert(shop)
        }
    }

    fun update(shop: Shop) {
        CoroutineScope(IO).launch {
            repo.update(shop)
        }
    }

    fun delete(shop: Shop) {
        CoroutineScope(IO).launch {
            repo.delete(shop.id)
        }
    }
}