package com.example.helloworld

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(var name: String, var price: Double, var amount: Int, var isBought: Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}