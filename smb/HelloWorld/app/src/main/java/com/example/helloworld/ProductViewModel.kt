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

class ProductViewModel(app: Application, mParam: String) : AndroidViewModel(app) {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repo: ProductRepository
    val allProducts: MutableList<Product>
    private val user: FirebaseUser?

    init {
        user = mAuth.currentUser
        allProducts = arrayListOf()
        val database = FirebaseDatabase.getInstance()
        val reference : DatabaseReference = if(mParam == "my") {
            database.getReference("users/${user?.uid}/products")
        } else {
            database.getReference("shared/products")
        }

        var name: String
        var price: Double
        var amount: Long
        var bought: Boolean
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (product in snapshot.children) {
                    name = product.child("name").value as String
                    price = product.child("price").value as Double
                    amount = product.child("amount").value as Long
                    bought = product.child("bought").value as Boolean
                    val newProduct = Product(name, price, amount, bought)
                    newProduct.id = product.child("id").value as String
                    if (!allProducts.contains(newProduct)) {
                        allProducts.add(newProduct)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadPost:onCancelled", error.toException())
            }
        })

        repo = ProductRepository(reference)
    }

    fun insert(product: Product) {
        CoroutineScope(IO).launch {
            repo.insert(product)
        }
    }

    fun update(product: Product) {
        CoroutineScope(IO).launch {
            repo.update(product)
        }
    }

    fun delete(product: Product) {
        CoroutineScope(IO).launch {
            repo.delete(product.id)
        }
    }
}