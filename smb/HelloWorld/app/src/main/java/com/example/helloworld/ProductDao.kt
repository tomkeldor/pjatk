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
    suspend fun insert(product: Product) : Long

    @Insert
    fun insertP(product: Product) : Long

    @Update
    suspend fun update(product: Product) : Int

    @Update
    fun updateP(product: Product) : Int

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM product_table WHERE id = :productId")
    suspend fun delete(productId: Long) : Int

    @Query("DELETE FROM product_table WHERE id = :productId")
    fun deleteP(productId: Long) : Int

    @Query("DELETE FROM product_table")
    suspend fun clear()
}