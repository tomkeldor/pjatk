package com.example.helloworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        val productDao = ProductDB.getDatabase(app).productDao()
        repo = ProductRepository(productDao)
        allProducts = repo.allProducts
    }

    fun insert(product: Product) = repo.insert(product)
    fun update(product: Product) = repo.update(product)
    fun delete(product: Product) = repo.delete(product)
    fun clear() = repo.clear()
}