package com.example.helloworld

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    fun getProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table")
    fun findAll(): Cursor

    @Insert
    fun insert(product: Product) : Long

    @Update
    fun update(product: Product) : Int

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product_table WHERE id = :productId")
    fun delete(productId: Long) : Int

    @Query("DELETE FROM product_table")
    fun clear()
}