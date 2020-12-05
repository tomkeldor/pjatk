package com.example.helloworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.security.Policy

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        val productDao = ProductDB.getDatabase(app).productDao()
        repo = ProductRepository(productDao)
        allProducts = repo.allProducts
    }

    fun get(productId: Long): Product {
        return repo.get(productId)
    }

    fun insert(product: Product) : Long {
        var id : Long = 0
        CoroutineScope(IO).launch {
            id = repo.insert(product)
        }
        return id
    }

    fun update(product: Product) {
        CoroutineScope(IO).launch {
            repo.update(product)
        }
    }

    fun delete(product: Product) {
        CoroutineScope(IO).launch {
            repo.delete(product)
        }
    }

    fun clear() {
        CoroutineScope(IO).launch {
            repo.clear()
        }
    }
}