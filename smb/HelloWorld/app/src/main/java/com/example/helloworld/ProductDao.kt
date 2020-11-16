package com.example.helloworld

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    fun getProducts(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product_table")
    fun clear()
}