package com.example.helloworld

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: LiveData<List<Product>> = productDao.getProducts()

    fun get(productId: Long) : Product {
        return productDao.get(productId)
    }

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun update(product: Product) {
        productDao.update(product)
    }

    suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    suspend fun clear() {
        productDao.clear()
    }
}