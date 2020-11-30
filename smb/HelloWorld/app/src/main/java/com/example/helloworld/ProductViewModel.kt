package com.example.helloworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        val productDao = ProductDB.getDatabase(app).productDao()
        repo = ProductRepository(productDao)
        allProducts = repo.allProducts
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
            repo.delete(product)
        }
    }
    fun clear() {
        CoroutineScope(IO).launch {
            repo.clear()
        }
    }
}