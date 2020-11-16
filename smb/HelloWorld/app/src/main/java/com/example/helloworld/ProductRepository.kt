package com.example.helloworld

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: LiveData<List<Product>> = productDao.getProducts()

    fun insert(product: Product){
        productDao.insert(product)
    }
    
    fun update(product: Product){
        productDao.update(product)
    }

    fun delete(product: Product){
        productDao.delete(product)
    }

    fun clear(){
        productDao.clear()
    }
}